package com.vasylenko.application.formdata;

import com.vasylenko.application.model.Email;
import com.vasylenko.application.model.user.parameters.CreateUserParameters;
import com.vasylenko.application.model.user.UserName;
import com.vasylenko.application.validation.PasswordsMatch;
import com.vasylenko.application.validation.ValidationGroupTwo;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

/**
 * Form data for creating a user.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@PasswordsMatch(groups = ValidationGroupTwo.class)
public class CreateUserFormData extends BaseUserFormData {

    @NotBlank
    private String password;

    @NotBlank
    private String passwordRepeated;

    /**
     * Converts the form data to create user parameters.
     *
     * @return the create user parameters
     */
    public CreateUserParameters toParameters() {
        CreateUserParameters parameters = new CreateUserParameters(
                new UserName(getFirstName(), getLastName()),
                password,
                getGender(),
                getBirthday(),
                new Email(getEmail()),
                getPhoneNumber());

        MultipartFile avatarFile = getAvatarFile();
        if (avatarFile != null && !avatarFile.isEmpty()) {
            parameters.setAvatar(avatarFile);
        }
        return parameters;
    }
}
