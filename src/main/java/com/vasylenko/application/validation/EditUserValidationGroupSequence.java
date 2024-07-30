package com.vasylenko.application.validation;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

/**
 * Defines the validation group sequence for editing a user.
 * The validation will be performed in the order of Default and ValidationGroupOne.
 */
@GroupSequence({Default.class, ValidationGroupOne.class})
public interface EditUserValidationGroupSequence {
}
