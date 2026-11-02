package io.github.Maike2961.validation_cpf.application.ports.out;

import io.github.Maike2961.validation_cpf.adapters.in.consumer.message.CpfValidationMessage;

public interface SendCpfValidatedOutputPort {
    void send(CpfValidationMessage message);
}
