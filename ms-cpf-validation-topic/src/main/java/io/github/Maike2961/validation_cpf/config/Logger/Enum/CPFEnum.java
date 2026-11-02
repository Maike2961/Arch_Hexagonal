package io.github.Maike2961.validation_cpf.config.Logger.Enum;

import io.github.Maike2961.validation_cpf.config.Logger.Interface.LogEventType;

public enum CPFEnum implements LogEventType {
    VALIDATE_CPF,
    INVALID_CPF,
    FORMAT_CPF;

    @Override
    public String getCode() {
        return this.name();
    }
}
