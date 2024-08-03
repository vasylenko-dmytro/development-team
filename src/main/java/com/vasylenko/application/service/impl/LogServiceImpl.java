package com.vasylenko.application.service.impl;

import com.vasylenko.application.model.document.LogEntry;
import com.vasylenko.application.repository.LogRepository;
import com.vasylenko.application.service.LogService;
import com.vasylenko.application.util.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service class for logging events.
 */
@Service
@Transactional
public class LogServiceImpl implements LogService {

    private final LogRepository repository;
    //private final CustomLogger customLogger;

    /**
     * Constructor-based dependency injection for the log service.
     */
    @Autowired
    public LogServiceImpl(LogRepository repository/*, CustomLogger customLogger*/) {
        this.repository = repository;
        //this.customLogger = customLogger;
    }

    /**
     * Logs a message with the specified log level.
     * @param level the log level
     * @param message the log message
     */
    @Override
    public void log(String level, String message) {
        LogEntry logEntry = new LogEntry();
        logEntry.setLevel(level);
        logEntry.setMessage(message);
        logEntry.setTimestamp(LocalDateTime.now());
        this.repository.save(logEntry);

        //customLogger.info(String.format("Logged message with level: %s saved successfully", level));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LogEntry> getLogs(Pageable pageable) {
        //customLogger.info(String.format("Retrieving paginated list of logs with pagination: %s", pageable));

        return repository.findAll(pageable);
    }

    @Override
    public Optional<LogEntry> getLog(String logId) {
        //customLogger.info(String.format("Retrieving log with ID: %s", logId));

        return repository.findById(logId);
    }

    @Override
    public void deleteLog(String logId) {
        //customLogger.info(String.format("Deleting log with ID: %s", logId));

        repository.deleteById(logId);

        //customLogger.info(String.format("Log with ID: %s deleted successfully", logId));
    }

    @Override
    public void deleteAllLogs() {
        //customLogger.info("Deleting all logs");

        repository.deleteAll();

        //customLogger.info("All logs deleted successfully");
    }
}
