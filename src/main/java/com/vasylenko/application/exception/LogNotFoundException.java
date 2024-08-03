package com.vasylenko.application.exception;

import com.vasylenko.application.model.user.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a log is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class LogNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with the specified user ID.
     *
     * @param logId the ID of the user that was not found
     */
    public LogNotFoundException(String logId) {
        super(String.format("Log with id %s not found", logId));
    }
}
