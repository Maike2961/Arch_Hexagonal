package io.github.Maike2961.hexagonal.useCase;

import io.github.Maike2961.hexagonal.adapters.in.consumer.message.CustomerMessage;
import io.github.Maike2961.hexagonal.adapters.out.client.FindAddressByZipCodeClient;
import io.github.Maike2961.hexagonal.adapters.out.client.response.AddressResponse;
import io.github.Maike2961.hexagonal.application.core.domain.Address;
import io.github.Maike2961.hexagonal.application.core.domain.Customer;
import io.github.Maike2961.hexagonal.application.core.exception.ZipCodeException;
import io.github.Maike2961.hexagonal.application.core.usecase.InsertCustomerUseCase;
import io.github.Maike2961.hexagonal.application.ports.out.FindAddressZipCodeOutputPort;
import io.github.Maike2961.hexagonal.application.ports.out.InsertCustomerOutputPort;
import io.github.Maike2961.hexagonal.application.ports.out.SendCpfForValidationOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class InsertCustomerUseCaseTest {

    @Mock
    private FindAddressZipCodeOutputPort findAddressZipCodeOutputPort;

    @Mock
    private InsertCustomerOutputPort insertCustomerOutputPort;

    @Mock
    private FindAddressByZipCodeClient findAddressByZipCodeClient;

    @Mock
    private SendCpfForValidationOutputPort sendCpfForValidationOutputPort;

    @InjectMocks
    private InsertCustomerUseCase useCase;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId("1234");
        customer.setName("John Doe");
        customer.setCpf("12345678900");
        customer.setAddress(null);
        customer.setIsValidCpf(false);
    }

    @Nested
    class FindAddressByZipCodeClientTests {

        @Test
        @DisplayName("should return AddressResponse when zip code is valid")
        void shouldReturnAddressResponseWhenZipCodeIsValid() {
            String zipCode = "12345";
            AddressResponse expectedResponse = createAddressResponse();

            when(findAddressByZipCodeClient.find(zipCode)).thenReturn(expectedResponse);

            AddressResponse actualResponse = findAddressByZipCodeClient.find(zipCode);

            assertEquals(expectedResponse.getStreet(), actualResponse.getStreet());
            assertEquals(expectedResponse.getCity(), actualResponse.getCity());
            assertEquals(expectedResponse.getState(), actualResponse.getState());
        }

        @Test
        @DisplayName("should throw ZipCodeException when zip code is invalid")
        void shouldThrowZipCodeExceptionWhenZipCodeIsInvalid() {
            String invalidZipCode = "invalid_zip";

            when(findAddressByZipCodeClient.find(invalidZipCode))
                    .thenThrow(new ZipCodeException(invalidZipCode));

            assertThrows(ZipCodeException.class, () -> findAddressByZipCodeClient.find(invalidZipCode));
        }

    }

    @Nested
    class InsertCustomerUseCaseTests {
        // Here you can add your test methods for InsertCustomerUseCase

        @Test
        @DisplayName("should insert customer and send for validation")
        void shouldInsertCustomerAndSendForValidation() {

            Address address = createAddress();

            when(findAddressZipCodeOutputPort.find("12345"))
                    .thenReturn(address);

            when(insertCustomerOutputPort.insert(customer))
                    .thenReturn(customer);

            useCase.insert(customer, "12345");

            CustomerMessage expectedMessage = new CustomerMessage(
                    customer.getId(),
                    customer.getName(),
                    "12345",
                    customer.getCpf(),
                    false
            );
            assertEquals(address, customer.getAddress());
            verify(findAddressZipCodeOutputPort, times(1))
                    .find("12345");
            verify(insertCustomerOutputPort, times(1)).insert(customer);
            verify(sendCpfForValidationOutputPort, times(1)).send(expectedMessage);
        }


        @Test
        @DisplayName("should send Correct CustomerMessage to validation topic")
        void shouldSendCorrectCustomerMessageToValidationTopic() {
            Address address = createAddress();

            when(findAddressZipCodeOutputPort.find("12345"))
                    .thenReturn(address);

            when(insertCustomerOutputPort.insert(customer))
                    .thenReturn(customer);

            ArgumentCaptor<CustomerMessage> messageCaptor = ArgumentCaptor.forClass(CustomerMessage.class);

            useCase.insert(customer, "12345");

            verify(sendCpfForValidationOutputPort).send(messageCaptor.capture());

            CustomerMessage capturedMessage = messageCaptor.getValue();

            assertEquals(customer.getId(), capturedMessage.getId());
            assertEquals(customer.getName(), capturedMessage.getName());
            assertEquals("12345", capturedMessage.getZipCode());
            assertEquals(customer.getCpf(), capturedMessage.getCpf());
            assertEquals(customer.getAddress().getStreet(), "123 Main St");
            assertEquals(customer.getAddress().getCity(), "Anytown");
            assertEquals(customer.getAddress().getState(), "CA");
            assertEquals(false, capturedMessage.getIsValidCpf());
        }
    }

    private Address createAddress() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        return address;
    }

    private AddressResponse createAddressResponse() {
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setStreet("123 Main St");
        addressResponse.setCity("Anytown");
        addressResponse.setState("CA");
        return addressResponse;
    }

}

