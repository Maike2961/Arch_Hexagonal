package io.github.Maike2961.validation_cpf.application.ports.in;

import io.github.Maike2961.validation_cpf.adapters.in.consumer.message.CpfValidationMessage;
import io.github.Maike2961.validation_cpf.application.core.domain.CpfValidation;

public interface ValidatedCPFInputPort {
    void validate(CpfValidation message);
}
