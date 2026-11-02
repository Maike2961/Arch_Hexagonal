package io.github.Maike2961.validation_cpf.kafkaTest;

import io.github.Maike2961.validation_cpf.adapters.in.consumer.ReceiveValidationCpfConsumer;
import io.github.Maike2961.validation_cpf.adapters.in.consumer.mapper.CPFValidateMapper;
import io.github.Maike2961.validation_cpf.adapters.in.consumer.message.CpfValidationMessage;
import io.github.Maike2961.validation_cpf.application.core.domain.CpfValidation;
import io.github.Maike2961.validation_cpf.application.ports.in.ValidatedCPFInputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReceiveValidationCpfConsumerTest {

    @Mock
    private ValidatedCPFInputPort validatedCPFInputPort;

    @InjectMocks
    private ReceiveValidationCpfConsumer receiveValidationCpfConsumer;

    private CpfValidationMessage message;

    @BeforeEach
    void setUp() {
        message = new CpfValidationMessage();
        message.setId("1234");
        message.setName("John Doe");
        message.setZipCode("12345-678");
        message.setCpf("123.456.789-00");
        message.setIsValidCpf(false);
    }

    @Test
    @DisplayName("should receive a message from the Kafka topic and process it")
    void shouldReceiveMessageFromKafkaTopicAndProcessIt() {
        CpfValidation cpfValidation = new CpfValidation();
        cpfValidation.setId(message.getId());
        cpfValidation.setName(message.getName());
        cpfValidation.setCpf(message.getCpf());
        cpfValidation.setIsValidCpf(message.getIsValidCpf());

        receiveValidationCpfConsumer.receive(message);

        verify(validatedCPFInputPort).validate(
                argThat(validation ->
                        validation.getId().equals(cpfValidation.getId()) &&
                        validation.getName().equals(cpfValidation.getName()) &&
                        validation.getCpf().equals(cpfValidation.getCpf()) &&
                        validation.getIsValidCpf().equals(cpfValidation.getIsValidCpf())
                )
        );
    }
}
