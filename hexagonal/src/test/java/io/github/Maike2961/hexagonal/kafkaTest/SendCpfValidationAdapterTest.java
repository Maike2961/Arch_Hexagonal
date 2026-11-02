package io.github.Maike2961.hexagonal.kafkaTest;

import io.github.Maike2961.hexagonal.adapters.in.consumer.message.CustomerMessage;
import io.github.Maike2961.hexagonal.adapters.out.SendCpfValidationAdapter;
import io.github.Maike2961.hexagonal.application.core.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("SendCpfValidationAdapterTest")
@ExtendWith(MockitoExtension.class)
public class SendCpfValidationAdapterTest {

    @Mock
    private KafkaTemplate<String, CustomerMessage> kafkaTemplate;

    @InjectMocks
    private SendCpfValidationAdapter sendCpfValidationAdapter;

    private CustomerMessage message;

    @BeforeEach
    void setUp() {
        message = new CustomerMessage();
        message.setId("1234");
        message.setName("John Doe");
        message.setZipCode("12345-678");
        message.setCpf("12345678900");
        message.setIsValidCpf(false);
    }

    @Test
    @DisplayName("should send a message to the Kafka topic")
    void shouldSendMessageTokafkaTopic() {

        when(kafkaTemplate.send(anyString(), any(CustomerMessage.class))).thenReturn(null);

        sendCpfValidationAdapter.send(message);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<CustomerMessage> messageCaptor = ArgumentCaptor.forClass(CustomerMessage.class);

        verify(kafkaTemplate, times(1)).send(captor.capture(), messageCaptor.capture());

        assertEquals("tp-cpf-validation", captor.getValue());
        assertEquals(message.getId(), messageCaptor.getValue().getId());
        assertEquals(message.getName(), messageCaptor.getValue().getName());
        assertEquals(message.getZipCode(), messageCaptor.getValue().getZipCode());
        assertEquals(message.getCpf(), messageCaptor.getValue().getCpf());
    }
}
