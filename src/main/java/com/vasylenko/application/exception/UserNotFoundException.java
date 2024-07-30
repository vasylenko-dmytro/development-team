package com.vasylenko.application.exception;

import com.vasylenko.application.model.user.UserId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a user is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with the specified user ID.
     *
     * @param userId the ID of the user that was not found
     */
    public UserNotFoundException(UserId userId) {
        super(String.format("User with id %s not found", userId.asString()));
    }
}
