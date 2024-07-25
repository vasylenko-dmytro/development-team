package com.vasylenko.application.exception;

public class UserServiceException extends RuntimeException {
    public UserServiceException(Exception e) {
        super(e);
    }
}
