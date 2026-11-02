package io.github.Maike2961.validation_cpf.config;

import io.github.Maike2961.validation_cpf.adapters.out.SendCpfValidatedAdapter;
import io.github.Maike2961.validation_cpf.adapters.out.ValidateCPFAdapter;
import io.github.Maike2961.validation_cpf.application.core.usecase.ValidateCpfUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCPFConfig {

    @Bean
    public ValidateCpfUseCase validateCpfUseCase(
            ValidateCPFAdapter validateCPFAdapter,
            SendCpfValidatedAdapter sendCpfValidatedAdapter
    ) {
        return new ValidateCpfUseCase(validateCPFAdapter, sendCpfValidatedAdapter);
    }
}
