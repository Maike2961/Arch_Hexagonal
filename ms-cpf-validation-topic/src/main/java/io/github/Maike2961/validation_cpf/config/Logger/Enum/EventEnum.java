package io.github.Maike2961.validation_cpf.config.Logger.Enum;

import io.github.Maike2961.validation_cpf.config.Logger.Interface.LogEventType;

public enum EventEnum implements LogEventType {
    SENT_CPF_VALIDATED,
    RECEIVE_CPF_FOR_VALIDATION;

    @Override
    public String getCode() {
        return this.name();
    }
}
