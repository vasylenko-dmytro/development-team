package com.vasylenko.application.validation;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

/**
 * Defines the validation group sequence for creating a user.
 * The validation will be performed in the order of Default, ValidationGroupOne, and ValidationGroupTwo.
 */
@GroupSequence({Default.class, ValidationGroupOne.class, ValidationGroupTwo.class})
public interface CreateUserValidationGroupSequence {
}