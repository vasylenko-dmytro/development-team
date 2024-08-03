package com.vasylenko.application.util;

import com.vasylenko.application.service.impl.LogServiceImpl;
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
    private final LogServiceImpl logServiceImpl;

    /**
     * Constructor-based dependency injection for the custom logger.
     */
    @Autowired
    public CustomLogger(LogServiceImpl logServiceImpl) {
        this.logServiceImpl = logServiceImpl;
    }

    /**
     * Logs the application startup message when the application is ready.
     */
    @EventListener({ApplicationReadyEvent.class})
    public void logStartup() {
        this.logServiceImpl.log("INFO", "Application started");
    }

    /**
     * Logs a message with the DEBUG log level.
     * @param message the log message
     */
    public void debug(String message) {
        this.logServiceImpl.log("DEBUG", message);
    }

    /**
     * Logs a message with the INFO log level.
     * @param message the log message
     */
    public void info(String message) {
        this.logServiceImpl.log("INFO", message);
    }

    /**
     * Logs a message with the WARN log level.
     * @param message the log message
     */
    public void warn(String message) {
        this.logServiceImpl.log("WARN", message);
    }

    /**
     * Logs a message with the ERROR log level.
     * @param message the log message
     */
    public void error(String message) {
        this.logServiceImpl.log("ERROR", message);
    }
}
