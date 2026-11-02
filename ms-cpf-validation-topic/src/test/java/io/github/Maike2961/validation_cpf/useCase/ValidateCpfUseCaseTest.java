package io.github.Maike2961.validation_cpf.useCase;

import io.github.Maike2961.validation_cpf.adapters.in.consumer.message.CpfValidationMessage;
import io.github.Maike2961.validation_cpf.application.core.domain.CpfValidation;
import io.github.Maike2961.validation_cpf.application.core.usecase.ValidateCpfUseCase;
import io.github.Maike2961.validation_cpf.application.ports.out.SendCpfValidatedOutputPort;
import io.github.Maike2961.validation_cpf.application.ports.out.ValidatedCPFOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ValidateCpfUseCaseTest {

    @Mock
    private ValidatedCPFOutputPort validatedCPFOutputPort;

    @Mock
    private SendCpfValidatedOutputPort sendCpfValidatedOutputPort;

    @InjectMocks
    private ValidateCpfUseCase useCase;

    private CpfValidation message;

    @BeforeEach
    void setUp() {
        message = new CpfValidation();
        message.setId("1234");
        message.setName("John Doe");
        message.setZipCode("12345-678");
        message.setCpf("123.456.789-00");
        message.setIsValidCpf(false);
    }

    @Test
    @DisplayName("should validate CPF and send the result")
    void shouldValidateCpfAndSendResult() {
        when(validatedCPFOutputPort.validatedCPF(message.getCpf())).thenReturn("123.456.789-00");

        useCase.validate(message);

        CpfValidationMessage expectedMessageToSend = new CpfValidationMessage(message.getId(), message.getName(), message.getZipCode(), "123.456.789-00", true);

        verify(sendCpfValidatedOutputPort, times(1)).send(expectedMessageToSend);
        verify(validatedCPFOutputPort, times(1)).validatedCPF(message.getCpf());
    }

    @Test
    @DisplayName("should validate invalid CPF and send the result")
    void shouldValidateInvalidCpfAndSendResult() {
        when(validatedCPFOutputPort.validatedCPF(message.getCpf())).thenReturn("CPF inválido");

        useCase.validate(message);

        CpfValidationMessage expectedMessageToSend = new CpfValidationMessage(message.getId(), message.getName(), message.getZipCode(), "CPF inválido", false);

        verify(sendCpfValidatedOutputPort, times(1)).send(expectedMessageToSend);
        verify(validatedCPFOutputPort, times(1)).validatedCPF(message.getCpf());
    }
}
