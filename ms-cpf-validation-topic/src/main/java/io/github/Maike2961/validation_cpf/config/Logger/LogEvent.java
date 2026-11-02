package io.github.Maike2961.validation_cpf.config.Logger;

import io.github.Maike2961.validation_cpf.config.Logger.Interface.LogEventType;

public record LogEvent(
        LogEventType event,
        String service,
        String message,
        String level
) {
}
