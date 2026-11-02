package io.github.Maike2961.hexagonal.config;

import io.github.Maike2961.hexagonal.adapters.out.FindAddressByZipCodeAdapter;
import io.github.Maike2961.hexagonal.adapters.out.InsertCustomerAdapter;
import io.github.Maike2961.hexagonal.adapters.out.SendCpfValidationAdapter;
import io.github.Maike2961.hexagonal.application.core.usecase.InsertCustomerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsertCustomerConfig {

    @Bean
    public InsertCustomerUseCase insertCustomerUseCase(
            FindAddressByZipCodeAdapter findAddressByZipCodeAdapter,
            InsertCustomerAdapter insertCustomerAdapter,
            SendCpfValidationAdapter sendCpfValidationAdapter
    ) {
        return new InsertCustomerUseCase(findAddressByZipCodeAdapter, insertCustomerAdapter, sendCpfValidationAdapter);
    }

}
