package io.github.Maike2961.hexagonal.adapters.in.consumer;

import io.github.Maike2961.hexagonal.adapters.in.consumer.mapper.CustomerMessageMapper;
import io.github.Maike2961.hexagonal.adapters.in.consumer.message.CustomerMessage;
import io.github.Maike2961.hexagonal.application.ports.in.UpdateCustomerInputPort;
import io.github.Maike2961.hexagonal.config.logger.Enum.CPFEnum;
import io.github.Maike2961.hexagonal.config.logger.LogEvent;
import io.github.Maike2961.hexagonal.config.logger.AppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiveValidatedCpfConsumer {

    @Autowired
    private UpdateCustomerInputPort updateCustomerInputPort;

    @Autowired
    private CustomerMessageMapper customerMessageMapper;

    @KafkaListener(topics = "tp-cpf-validated", groupId = "arantes")
    public void receive(CustomerMessage customerMessage) {
        boolean isValidCpf = customerMessage.getIsValidCpf();
        if (!isValidCpf) {
            AppLogger.warn(new LogEvent(
                    CPFEnum.RECEIVE_CPF_INVALID_RESPONSE_TOPIC,
                    "ReceiveValidatedCpfConsumer",
                    "Recebendo CPF inválido para o cliente: " + customerMessage.getName(),
                    "WARN"
            ));
        } else {
            AppLogger.info(new LogEvent(
                    CPFEnum.RECEIVE_CPF_VALIDATED_RESPONSE_TOPIC,
                    "ReceiveValidatedCpfConsumer",
                    "Recebendo CPF validado para o cliente: " + customerMessage.getName(),
                    "INFO"
            ));
        }
        customerMessage.setIsValidCpf(isValidCpf);
        
        var customer = customerMessageMapper.toCustomer(customerMessage);
        updateCustomerInputPort.update(customer, customerMessage.getZipCode());
    }
}
