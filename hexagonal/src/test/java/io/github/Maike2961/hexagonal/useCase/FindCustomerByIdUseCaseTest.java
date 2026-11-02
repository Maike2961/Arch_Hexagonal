package io.github.Maike2961.hexagonal.useCase;

import io.github.Maike2961.hexagonal.application.core.domain.Customer;
import io.github.Maike2961.hexagonal.application.core.exception.CustomerNotFoundException;
import io.github.Maike2961.hexagonal.application.core.usecase.FindCustomerByIdUseCase;
import io.github.Maike2961.hexagonal.application.ports.out.FindCustomerByIdOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class FindCustomerByIdUseCaseTest {

    @Mock
    private FindCustomerByIdOutputPort findCustomerByIdOutputPort;

    @InjectMocks
    private FindCustomerByIdUseCase findCustomerByIdUseCase;

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
    @DisplayName("Should find customer by ID successfully")
    void shouldFindCustomerByIdSuccessfully() {
        String customerId = "1234";

        when(findCustomerByIdOutputPort.find(customerId)).thenReturn(Optional.of(customer));

        Customer customerResponse = findCustomerByIdUseCase.find(customerId);

        verify(findCustomerByIdOutputPort, times(1)).find(customerId);

        assertNotNull(customerResponse);
        assertEquals(customer.getId(), customerResponse.getId());
        assertEquals(customer.getName(), customerResponse.getName());
        assertEquals(customer.getCpf(), customerResponse.getCpf());
    }

    @Test
    @DisplayName("Should throw CustomerNotFoundException when customer is not found")
    void shouldThrowCustomerNotFoundExceptionWhenCustomerIsNotFound() {
        String customerId = "5678";

        when(findCustomerByIdOutputPort.find(customerId))
                .thenReturn(Optional.empty())
                .thenThrow(new CustomerNotFoundException(customerId));

        assertThrows(CustomerNotFoundException.class, () -> findCustomerByIdUseCase.find(customerId));
        verify(findCustomerByIdOutputPort, times(1)).find(customerId);
    }
}
