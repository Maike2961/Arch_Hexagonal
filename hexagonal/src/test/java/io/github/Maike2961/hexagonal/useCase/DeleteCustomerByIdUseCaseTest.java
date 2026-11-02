package io.github.Maike2961.hexagonal.useCase;

import io.github.Maike2961.hexagonal.application.core.domain.Customer;
import io.github.Maike2961.hexagonal.application.core.usecase.DeleteCustomerByIdUseCase;
import io.github.Maike2961.hexagonal.application.ports.in.FindCustomerByIdInputPort;
import io.github.Maike2961.hexagonal.application.ports.out.DeleteCustomerByIdOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class DeleteCustomerByIdUseCaseTest {

    @Mock
    private FindCustomerByIdInputPort findCustomerByIdInputPort;

    @Mock
    private DeleteCustomerByIdOutputPort deleteCustomerByIdOutputPort;

    @InjectMocks
    private DeleteCustomerByIdUseCase useCase;

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
    @DisplayName("Should delete customer by ID successfully")
    void shouldDeleteCustomerByIdSuccessfully() {

        when(findCustomerByIdInputPort.find(customer.getId())).thenReturn(customer);

        useCase.delete(customer.getId());

        verify(findCustomerByIdInputPort, times(1)).find(customer.getId());
    }

}
