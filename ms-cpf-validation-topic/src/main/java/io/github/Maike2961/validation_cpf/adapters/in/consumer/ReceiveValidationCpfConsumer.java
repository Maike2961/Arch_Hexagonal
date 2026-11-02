package io.github.Maike2961.validation_cpf.adapters.in.consumer;

import io.github.Maike2961.validation_cpf.adapters.in.consumer.mapper.CPFValidateMapper;
import io.github.Maike2961.validation_cpf.adapters.in.consumer.message.CpfValidationMessage;
import io.github.Maike2961.validation_cpf.application.ports.in.ValidatedCPFInputPort;
import io.github.Maike2961.validation_cpf.config.Logger.AppLogger;
import io.github.Maike2961.validation_cpf.config.Logger.Enum.EventEnum;
import io.github.Maike2961.validation_cpf.config.Logger.LogEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiveValidationCpfConsumer {

    @Autowired
    private ValidatedCPFInputPort validatedCPFInputPort;

    @KafkaListener(topics = "tp-cpf-validation", groupId = "arantes")
    public void receive(CpfValidationMessage message) {
        var cpf = CPFValidateMapper.toMessage(message);
        validatedCPFInputPort.validate(cpf);
        
        AppLogger.info(new LogEvent(
                EventEnum.RECEIVE_CPF_FOR_VALIDATION,
                "ReceiveValidationCpfConsumer",
                "Recebendo CPF para validação: " + message.getCpf(),
                "INFO"
        ));
    }
}
