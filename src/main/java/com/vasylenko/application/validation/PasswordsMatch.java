package com.vasylenko.application.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to validate that the passwords match.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsMatchValidator.class)
public @interface PasswordsMatch {

    /**
     * Error message to be returned if the passwords do not match.
     *
     * @return the error message
     */
    String message() default "{PasswordsNotMatching}";

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
