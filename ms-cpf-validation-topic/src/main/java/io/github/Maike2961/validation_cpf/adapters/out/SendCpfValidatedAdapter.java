package io.github.Maike2961.validation_cpf.adapters.out;

import io.github.Maike2961.validation_cpf.adapters.in.consumer.message.CpfValidationMessage;
import io.github.Maike2961.validation_cpf.application.ports.out.SendCpfValidatedOutputPort;
import io.github.Maike2961.validation_cpf.config.Logger.AppLogger;
import io.github.Maike2961.validation_cpf.config.Logger.Enum.EventEnum;
import io.github.Maike2961.validation_cpf.config.Logger.LogEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SendCpfValidatedAdapter implements SendCpfValidatedOutputPort {

    @Autowired
    private KafkaTemplate<String, CpfValidationMessage> kafkaTemplate;

    @Override
    public void send(CpfValidationMessage message) {
       kafkaTemplate.send("tp-cpf-validated", message);
        AppLogger.info(new LogEvent(
                EventEnum.SENT_CPF_VALIDATED,
                "SendCpfValidatedAdapter",
                "Enviando CPF validado para o tópico: " + message.getCpf(),
                "INFO"
        ));
    }
}

