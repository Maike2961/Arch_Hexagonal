package io.github.Maike2961.hexagonal.config.logger;

import io.github.Maike2961.hexagonal.config.logger.Interface.LogEventType;

public record LogEvent(
        LogEventType event,
        String service,
        String message,
        String level
) {
}
