package io.github.Maike2961.hexagonal.config.logger;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger ;

public class AppLogger {
    private static final Logger log = LoggerFactory.getLogger(Logger.class);
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
    public static void warn(LogEvent logEvent) {
        log.warn("Event: {}, Service: {}, Message: {}, Level: {}", logEvent.event(), logEvent.service(), logEvent.message(), logEvent.level());
    }
}
