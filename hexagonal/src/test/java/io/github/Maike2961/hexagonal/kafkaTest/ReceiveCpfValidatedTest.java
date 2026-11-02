package io.github.Maike2961.hexagonal.kafkaTest;

import io.github.Maike2961.hexagonal.adapters.in.consumer.ReceiveValidatedCpfConsumer;
import io.github.Maike2961.hexagonal.adapters.in.consumer.mapper.CustomerMessageMapper;
import io.github.Maike2961.hexagonal.adapters.in.consumer.message.CustomerMessage;
import io.github.Maike2961.hexagonal.application.core.domain.Customer;
import io.github.Maike2961.hexagonal.application.ports.in.UpdateCustomerInputPort;
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
public class ReceiveCpfValidatedTest {

    @Mock
    private UpdateCustomerInputPort updateCustomerInputPort;

    @Mock
    private CustomerMessageMapper customerMessageMapper;

    @InjectMocks
    private ReceiveValidatedCpfConsumer receiveValidatedCpfConsumer;

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
    @DisplayName("should receive a message from the Kafka topic and process it")
    void shouldReceiveMessageFromKafkaTopicAndProcessIt() {
        Customer customer = new Customer();
        customer.setId(message.getId());
        customer.setName(message.getName());
        customer.setCpf(message.getCpf());
        customer.setIsValidCpf(message.getIsValidCpf());

        when(customerMessageMapper.toCustomer(message)).thenReturn(customer);
        doNothing()
                .when(updateCustomerInputPort)
                .update(customer, message.getZipCode());

        receiveValidatedCpfConsumer.receive(message);

        verify(customerMessageMapper, times(1)).toCustomer(message);
        verify(updateCustomerInputPort, times(1)).update(customer, message.getZipCode());
    }
}
