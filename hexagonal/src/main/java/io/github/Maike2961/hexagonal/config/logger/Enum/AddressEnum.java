package io.github.Maike2961.hexagonal.config.logger.Enum;

import io.github.Maike2961.hexagonal.config.logger.Interface.LogEventType;

public enum AddressEnum implements LogEventType {
    ADDRESS_FOUND,
    ADDRESS_NOT_FOUND;

    @Override
    public String getCode() {
        return this.name();
    }
}
