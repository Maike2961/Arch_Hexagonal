package io.github.Maike2961.validation_cpf.application.core.usecase;

import io.github.Maike2961.validation_cpf.adapters.in.consumer.message.CpfValidationMessage;
import io.github.Maike2961.validation_cpf.application.core.domain.CpfValidation;
import io.github.Maike2961.validation_cpf.application.ports.in.ValidatedCPFInputPort;
import io.github.Maike2961.validation_cpf.application.ports.out.SendCpfValidatedOutputPort;
import io.github.Maike2961.validation_cpf.application.ports.out.ValidatedCPFOutputPort;

public class ValidateCpfUseCase implements ValidatedCPFInputPort {
    private final ValidatedCPFOutputPort validatedCPFOutputPort;
    private final SendCpfValidatedOutputPort sendCpfValidatedOutputPort;

    public ValidateCpfUseCase(ValidatedCPFOutputPort validatedCPFOutputPort, SendCpfValidatedOutputPort sendCpfValidatedOutputPort) {
        this.validatedCPFOutputPort = validatedCPFOutputPort;
        this.sendCpfValidatedOutputPort = sendCpfValidatedOutputPort;
    }

    @Override
    public void validate(CpfValidation message) {
        String cpfValidated = validatedCPFOutputPort.validatedCPF(message.getCpf());

        boolean isValid = !cpfValidated.equals("CPF inválido");
        if (isValid) {
            message.setCpf(cpfValidated);
        }
        message.setIsValidCpf(isValid);

        CpfValidationMessage messageToSend = new CpfValidationMessage(message.getId(), message.getName(), message.getZipCode(), message.getCpf(), message.getIsValidCpf());
        sendCpfValidatedOutputPort.send(messageToSend);
    }
}

