package io.github.Maike2961.hexagonal.useCase;

import io.github.Maike2961.hexagonal.adapters.out.client.response.AddressResponse;
import io.github.Maike2961.hexagonal.application.core.domain.Address;
import io.github.Maike2961.hexagonal.application.core.domain.Customer;
import io.github.Maike2961.hexagonal.application.core.usecase.UpdateCustomerUseCase;
import io.github.Maike2961.hexagonal.application.ports.in.FindCustomerByIdInputPort;
import io.github.Maike2961.hexagonal.application.ports.out.FindAddressZipCodeOutputPort;
import io.github.Maike2961.hexagonal.application.ports.out.FindCustomerByIdOutputPort;
import io.github.Maike2961.hexagonal.application.ports.out.InsertCustomerOutputPort;
import io.github.Maike2961.hexagonal.application.ports.out.UpdateCustomerOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UpdateCustomerUseCaseTest {
    @Mock
    private FindAddressZipCodeOutputPort findAddressZipCodeOutputPort;

    @Mock
    private UpdateCustomerOutputPort updateCustomerOutputPort;

    @Mock
    private FindCustomerByIdInputPort findCustomerByIdInputPort;

    @InjectMocks
    private UpdateCustomerUseCase updateCustomerUseCase;
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

    @Test
    @DisplayName("Should update customer successfully")
    void shouldUpdateCustomerSuccessfully() {

        Address address = createAddress();

        when(findCustomerByIdInputPort.find(customer.getId())).thenReturn(customer);
        when(findAddressZipCodeOutputPort.find("12345")).thenReturn(address);

        doNothing().when(updateCustomerOutputPort).update(customer);

        updateCustomerUseCase.update(customer, "12345");

        verify(findCustomerByIdInputPort, times(1)).find(customer.getId());
        verify(findAddressZipCodeOutputPort, times(1)).find("12345");
        verify(updateCustomerOutputPort, times(1)).update(customer);

    }
    private Address createAddress() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        return address;
    }
}
