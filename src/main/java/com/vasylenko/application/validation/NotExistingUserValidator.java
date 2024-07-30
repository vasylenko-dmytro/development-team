package com.vasylenko.application.validation;

import com.vasylenko.application.formdata.BaseUserFormData;
import com.vasylenko.application.model.Email;
import com.vasylenko.application.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Validator to check if a user with the given email already exists.
 */
@Component
public class NotExistingUserValidator implements ConstraintValidator<NotExistingUser, BaseUserFormData> {

    private final UserService userService;


    /**
     * Constructs a NotExistingUserValidator with the given UserService.
     *
     * @param userService the UserService to check for existing users
     */
    @Autowired
    public NotExistingUserValidator(UserService userService) {
        this.userService = userService;
    }

    public void initialize(NotExistingUser constraint) {
        // No initialization required
    }

    public boolean isValid(BaseUserFormData formData, ConstraintValidatorContext context) {
        if (userService.userWithEmailExists(new Email(formData.getEmail()))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{UserAlreadyExisting}")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}