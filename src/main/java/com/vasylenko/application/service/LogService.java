package com.vasylenko.application.service;

import com.vasylenko.application.model.document.LogEntry;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing logs.
 */
public interface LogService {

    /**
     * Logs a message with the specified log level.
     *
     * @param level the log level
     * @param message the log message
     */
    void log(String level, String message);

    /**
     * Retrieves a paginated list of logs.
     *
     * @param pageable the pagination information
     * @return a page of logs
     */
    Page<LogEntry> getLogs(Pageable pageable);

    /**
     * Retrieves a log by their ID.
     *
     * @param logId the ID of the log to retrieve
     * @return an optional containing the log if found
     */
    Optional<LogEntry> getLog(String logId);

    /**
     * Deletes a log by their ID.
     *
     * @param logId the ID of the log to delete
     */
    void deleteLog(String logId);

    /**
     * Deletes all logs.
     */
    void deleteAllLogs();
}
