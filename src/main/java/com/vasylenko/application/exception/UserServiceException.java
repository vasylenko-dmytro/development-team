package com.vasylenko.application.exception;

/**
 * Exception thrown when there is an issue with the user service.
 */
public class UserServiceException extends RuntimeException {

    /**
     * Constructs a new UserServiceException with the specified exception.
     *
     * @param e the exception that caused this exception
     */
    public UserServiceException(Exception e) {
        super(e);
    }
}
