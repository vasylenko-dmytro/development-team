package com.vasylenko.application.validation;

import com.vasylenko.application.formdata.BaseUserFormData;
import com.vasylenko.application.model.email.Email;
import com.vasylenko.application.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NotExistingUserValidator implements ConstraintValidator<NotExistingUser, BaseUserFormData> {

    private final UserService userService;

    @Autowired
    public NotExistingUserValidator(UserService userService) {
        this.userService = userService;
    }

    public void initialize(NotExistingUser constraint) {
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