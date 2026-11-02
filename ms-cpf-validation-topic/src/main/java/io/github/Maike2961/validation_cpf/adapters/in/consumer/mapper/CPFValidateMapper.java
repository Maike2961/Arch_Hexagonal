package io.github.Maike2961.validation_cpf.adapters.in.consumer.mapper;

import io.github.Maike2961.validation_cpf.adapters.in.consumer.message.CpfValidationMessage;
import io.github.Maike2961.validation_cpf.application.core.domain.CpfValidation;

public class CPFValidateMapper {
    public static CpfValidation toMessage(CpfValidationMessage cpfValidation) {
        return new CpfValidation(
                cpfValidation.getId(),
                cpfValidation.getName(),
                cpfValidation.getZipCode(),
                cpfValidation.getCpf(),
                cpfValidation.getIsValidCpf()
        );
    }

}
