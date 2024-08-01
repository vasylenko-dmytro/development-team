package com.vasylenko.application.service;

import com.vasylenko.application.model.document.LogEntry;
import com.vasylenko.application.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service class for logging messages.
 */
@Service
public class LogService {

    /**
     * Log repository for saving log entries.
     */
    private final LogRepository logRepository;

    /**
     * Constructor-based dependency injection for the log service.
     */
    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    /**
     * Logs a message with the specified log level.
     * @param level the log level
     * @param message the log message
     */
    public void log(String level, String message) {
        LogEntry logEntry = new LogEntry();
        logEntry.setLevel(level);
        logEntry.setMessage(message);
        logEntry.setTimestamp(LocalDateTime.now());
        this.logRepository.save(logEntry);
    }
}
