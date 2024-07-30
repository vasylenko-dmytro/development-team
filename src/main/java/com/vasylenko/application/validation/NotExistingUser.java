package com.vasylenko.application.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to validate that a user does not already exist.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotExistingUserValidator.class)
public @interface NotExistingUser {

    /**
     * Error message to be returned if the user already exists.
     *
     * @return the error message
     */
    String message() default "{UserAlreadyExisting}";

    /**
     * Groups for categorizing constraints.
     *
     * @return the groups
     */
    Class<?>[] groups() default {};

    /**
     * Payload for clients to specify additional information about the validation error.
     *
     * @return the payload
     */
    Class<? extends Payload>[] payload() default {};
}
