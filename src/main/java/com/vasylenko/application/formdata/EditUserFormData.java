package com.vasylenko.application.formdata;

import com.vasylenko.application.model.Email;
import com.vasylenko.application.model.user.parameters.EditUserParameters;
import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

/**
 * Form data for editing a user.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EditUserFormData extends BaseUserFormData {

    private String id;

    private long version;

    private String avatarBase64Encoded;

    /**
     * Creates an instance of EditUserFormData from a User object.
     *
     * @param user the user object
     * @return the EditUserFormData instance
     */
    public static EditUserFormData fromUser(User user) {
        EditUserFormData result = new EditUserFormData();
        result.setId(user.getId().asString());
        result.setVersion(user.getVersion());
        result.setFirstName(user.getUserName().getFirstName());
        result.setLastName(user.getUserName().getLastName());
        result.setGender(user.getGender());
        result.setBirthday(user.getBirthday());
        result.setEmail(user.getEmail().asString());
        result.setPhoneNumber(user.getPhoneNumber());

        if (user.getAvatar() != null) {
            String encoded = Base64.getEncoder().encodeToString(user.getAvatar());
            result.setAvatarBase64Encoded(encoded);
        }
        return result;
    }

    /**
     * Converts the form data to edit user parameters.
     *
     * @return the edit user parameters
     */
    public EditUserParameters toParameters() {
        EditUserParameters parameters = new EditUserParameters(
                version,
                new UserName(getFirstName(), getLastName()),
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
