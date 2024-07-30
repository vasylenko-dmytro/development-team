package com.vasylenko.application.validation;

import com.vasylenko.application.formdata.CreateUserFormData;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator to check if the passwords match in the CreateUserFormData.
 */
public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, CreateUserFormData> {

    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
        // No initialization required
    }

    @Override
    public boolean isValid(CreateUserFormData value, ConstraintValidatorContext context) {
        if (!value.getPassword().equals(value.getPasswordRepeated())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{PasswordsNotMatching}")
                    .addPropertyNode("passwordRepeated")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
