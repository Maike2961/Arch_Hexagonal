package io.github.Maike2961.hexagonal.config;

import io.github.Maike2961.hexagonal.adapters.out.FindAllCustomerAdapter;
import io.github.Maike2961.hexagonal.application.core.usecase.FindAllCustomerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindAllCustomerConfig {

    @Bean
    public FindAllCustomerUseCase findAllCustomerUseCase(
            FindAllCustomerAdapter findAllCustomerAdapter
    ) {
        return new FindAllCustomerUseCase(findAllCustomerAdapter);
    }
}
