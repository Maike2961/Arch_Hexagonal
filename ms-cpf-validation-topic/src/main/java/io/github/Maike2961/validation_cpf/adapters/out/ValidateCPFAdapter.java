package io.github.Maike2961.validation_cpf.adapters.out;

import io.github.Maike2961.validation_cpf.adapters.out.repository.ValidateCPFRepository;
import io.github.Maike2961.validation_cpf.application.ports.out.ValidatedCPFOutputPort;
import io.github.Maike2961.validation_cpf.config.Logger.AppLogger;
import io.github.Maike2961.validation_cpf.config.Logger.Enum.CPFEnum;
import io.github.Maike2961.validation_cpf.config.Logger.LogEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ValidateCPFAdapter implements ValidatedCPFOutputPort {
    @Override
    public String validatedCPF(String cpf) {
        if (!ValidateCPFRepository.isValidCPF(cpf)) {
            AppLogger.error(new LogEvent(
                    CPFEnum.INVALID_CPF,
                    "ValidateCpfUseCase",
                    "CPF inválido: " + cpf,
                    "ERROR"
            ));
            return "CPF inválido";
        }
        AppLogger.info(new LogEvent(
                CPFEnum.VALIDATE_CPF,
                "ValidateCpfUseCase",
                "CPF validado: " + cpf,
                "INFO"
        ));
        String cpfFormatted = ValidateCPFRepository.formatCPF(cpf);
        AppLogger.info(new LogEvent(
                CPFEnum.FORMAT_CPF,
                "ValidateCpfUseCase",
                "CPF formatado: " + cpfFormatted,
                "INFO"
        ));
        return cpfFormatted;
    }
}
