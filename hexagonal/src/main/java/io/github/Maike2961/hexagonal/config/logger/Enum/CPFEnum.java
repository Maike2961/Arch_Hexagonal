package io.github.Maike2961.hexagonal.config.logger.Enum;

import io.github.Maike2961.hexagonal.config.logger.Interface.LogEventType;

public enum CPFEnum implements LogEventType {
    SEND_CPF_TO_VALIDATION_TOPIC,
    RECEIVE_CPF_VALIDATED_RESPONSE_TOPIC,
    RECEIVE_CPF_INVALID_RESPONSE_TOPIC;

    @Override
    public String getCode() {
        return this.name();
    }
}
