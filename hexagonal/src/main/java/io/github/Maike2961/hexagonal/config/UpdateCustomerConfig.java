package io.github.Maike2961.hexagonal.config;

import io.github.Maike2961.hexagonal.adapters.out.FindAddressByZipCodeAdapter;
import io.github.Maike2961.hexagonal.adapters.out.FindCustomerByIdAdapter;
import io.github.Maike2961.hexagonal.adapters.out.InsertCustomerAdapter;
import io.github.Maike2961.hexagonal.adapters.out.UpdateCustomerAdapter;
import io.github.Maike2961.hexagonal.application.core.usecase.FindCustomerByIdUseCase;
import io.github.Maike2961.hexagonal.application.core.usecase.InsertCustomerUseCase;
import io.github.Maike2961.hexagonal.application.core.usecase.UpdateCustomerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateCustomerConfig {

    @Bean
    public UpdateCustomerUseCase updateCustomerUseCase(
            FindCustomerByIdUseCase findCustomerByIdUseCase,
            FindAddressByZipCodeAdapter findAddressByZipCodeAdapter,
            UpdateCustomerAdapter updateCustomerAdapter
    ) {
        return new UpdateCustomerUseCase(findCustomerByIdUseCase, findAddressByZipCodeAdapter, updateCustomerAdapter);
    }

}
