package io.github.Maike2961.hexagonal.config.logger.Enum;

import io.github.Maike2961.hexagonal.config.logger.Interface.LogEventType;

public enum CustomerEnum implements LogEventType {
    CUSTOMER_NOT_FOUND,
    CUSTOMER_DELETED,
    CUSTOMER_FOUND,
    CUSTOMER_NOT_DELETED,
    CUSTOMER_NOT_CREATED,
    CUSTOMER_CREATED,
    CUSTOMER_NOT_UPDATED,
    CUSTOMER_UPDATED;

    @Override
    public String getCode() {
        return this.name();
    }
}
