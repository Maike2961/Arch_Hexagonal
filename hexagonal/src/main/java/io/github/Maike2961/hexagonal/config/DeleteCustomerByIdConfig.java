package io.github.Maike2961.hexagonal.config;


import io.github.Maike2961.hexagonal.adapters.out.DeleteCustomerByIdAdapter;
import io.github.Maike2961.hexagonal.application.core.usecase.DeleteCustomerByIdUseCase;
import io.github.Maike2961.hexagonal.application.core.usecase.FindCustomerByIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteCustomerByIdConfig {

    @Bean
    public DeleteCustomerByIdUseCase deleteCustomerConfig(
            FindCustomerByIdUseCase findCustomerByIdUseCase,
            DeleteCustomerByIdAdapter deleteCustomerByIdAdapter
    ) {
        return new DeleteCustomerByIdUseCase(findCustomerByIdUseCase, deleteCustomerByIdAdapter);
    }
}
