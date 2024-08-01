package com.vasylenko.application.util;

import com.vasylenko.application.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Custom logger for the application.
 */
@Component
public class CustomLogger {

    /**
     * Log service for saving log entries.
     */
    private final LogService logService;

    /**
     * Constructor-based dependency injection for the custom logger.
     */
    @Autowired
    public CustomLogger(LogService logService) {
        this.logService = logService;
    }

    /**
     * Logs the application startup message when the application is ready.
     */
    @EventListener({ApplicationReadyEvent.class})
    public void logStartup() {
        this.logService.log("INFO", "Application started");
    }

    /**
     * Logs a message with the DEBUG log level.
     * @param message the log message
     */
    public void debug(String message) {
        this.logService.log("DEBUG", message);
    }

    /**
     * Logs a message with the INFO log level.
     * @param message the log message
     */
    public void info(String message) {
        this.logService.log("INFO", message);
    }

    /**
     * Logs a message with the WARN log level.
     * @param message the log message
     */
    public void warn(String message) {
        this.logService.log("WARN", message);
    }

    /**
     * Logs a message with the ERROR log level.
     * @param message the log message
     */
    public void error(String message) {
        this.logService.log("ERROR", message);
    }
}
