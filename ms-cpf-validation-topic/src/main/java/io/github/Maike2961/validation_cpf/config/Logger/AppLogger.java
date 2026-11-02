package io.github.Maike2961.validation_cpf.config.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {
    private static final Logger log = LoggerFactory.getLogger(AppLogger.class);
    private AppLogger(){}

    public static void info(LogEvent logEvent) {
        log.debug("Event: {}, Service: {}, Message: {}, Level: {}", logEvent.event(), logEvent.service(), logEvent.message(), logEvent.level());
    }
    public static void error(LogEvent logEvent) {
        log.error("Event: {}, Service: {}, Message: {}, Level: {}", logEvent.event(), logEvent.service(), logEvent.message(), logEvent.level());
    }
    public static void debug(LogEvent logEvent) {
        log.debug("Event: {}, Service: {}, Message: {}, Level: {}", logEvent.event(), logEvent.service(), logEvent.message(), logEvent.level());
    }

}
