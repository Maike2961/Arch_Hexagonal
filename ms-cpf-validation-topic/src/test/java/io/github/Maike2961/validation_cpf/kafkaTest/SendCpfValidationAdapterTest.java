package io.github.Maike2961.validation_cpf.kafkaTest;
import io.github.Maike2961.validation_cpf.adapters.out.SendCpfValidatedAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.springframework.kafka.core.KafkaTemplate;

import io.github.Maike2961.validation_cpf.adapters.in.consumer.message.CpfValidationMessage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SendCpfValidationAdapterTest {

    @Mock
    private KafkaTemplate<String, CpfValidationMessage> kafkaTemplate;

    @InjectMocks
    private SendCpfValidatedAdapter sendCpfValidatedAdapter;

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
    @DisplayName("should send a message to the Kafka topic")
    void shouldSendMessageToKafkaTopic() {
        when(kafkaTemplate.send(anyString(), any(CpfValidationMessage.class))).thenReturn(null);

        sendCpfValidatedAdapter.send(message);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<CpfValidationMessage> messageCaptor = ArgumentCaptor.forClass(CpfValidationMessage.class);

        verify(kafkaTemplate, times(1)).send(captor.capture(), messageCaptor.capture());

        assertEquals("tp-cpf-validated", captor.getValue());
        assertEquals(message.getId(), messageCaptor.getValue().getId());
        assertEquals(message.getName(), messageCaptor.getValue().getName());
        assertEquals(message.getZipCode(), messageCaptor.getValue().getZipCode());
        assertEquals(message.getCpf(), messageCaptor.getValue().getCpf());
    }
}
