package io.github.Maike2961.hexagonal.adapters.out;

import io.github.Maike2961.hexagonal.adapters.in.consumer.message.CustomerMessage;
import io.github.Maike2961.hexagonal.application.ports.out.SendCpfForValidationOutputPort;
import io.github.Maike2961.hexagonal.config.logger.AppLogger;
import io.github.Maike2961.hexagonal.config.logger.Enum.CPFEnum;
import io.github.Maike2961.hexagonal.config.logger.LogEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SendCpfValidationAdapter implements SendCpfForValidationOutputPort {

    @Autowired
    private KafkaTemplate<String, CustomerMessage> kafkaTemplate;

    @Override
    public void send(CustomerMessage message) {
        kafkaTemplate.send("tp-cpf-validation", message);

        AppLogger.info(
                new LogEvent(
                        CPFEnum.SEND_CPF_TO_VALIDATION_TOPIC,
                        "SendCpfValidationAdapter",
                        "Enviando CPF para validação: " + message.getCpf(),
                        "INFO"
                )
        );
    }
}
