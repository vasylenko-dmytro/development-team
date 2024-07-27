package com.vasylenko.application.formdata;

import com.vasylenko.application.model.Email;
import com.vasylenko.application.model.user.parameters.CreateUserParameters;
import com.vasylenko.application.model.user.UserName;
import com.vasylenko.application.validation.PasswordsMatch;
import com.vasylenko.application.validation.ValidationGroupTwo;
import jakarta.validation.constraints.NotBlank;

@PasswordsMatch(groups = ValidationGroupTwo.class)
public class CreateUserFormData extends BaseUserFormData {
    @NotBlank
    private String password;
    @NotBlank
    private String passwordRepeated;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeated() {
        return passwordRepeated;
    }

    public void setPasswordRepeated(String passwordRepeated) {
        this.passwordRepeated = passwordRepeated;
    }

    public CreateUserParameters toParameters() {
        CreateUserParameters parameters = new CreateUserParameters(new UserName(getFirstName(), getLastName()),
                password,
                getGender(),
                getBirthday(),
                new Email(getEmail()),
                getPhoneNumber());

        if (getAvatarFile() != null && !getAvatarFile().isEmpty()) {
            parameters.setAvatar(getAvatarFile());
        }
        return parameters;
    }
}
