package com.vasylenko.application.formdata;

import com.vasylenko.application.model.email.Email;
import com.vasylenko.application.model.user.EditUserParameters;
import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserName;

import java.util.Base64;

public class EditUserFormData extends BaseUserFormData {
    private String id;
    private long version;
    private String avatarBase64Encoded;

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

    public EditUserParameters toParameters() {
        EditUserParameters parameters = new EditUserParameters(version,
                new UserName(getFirstName(), getLastName()),
                getGender(),
                getBirthday(),
                new Email(getEmail()),
                getPhoneNumber());

        if (getAvatarFile() != null && !getAvatarFile().isEmpty()) {
            parameters.setAvatar(getAvatarFile());
        }

        return parameters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getAvatarBase64Encoded() {
        return avatarBase64Encoded;
    }

    public void setAvatarBase64Encoded(String avatarBase64Encoded) {
        this.avatarBase64Encoded = avatarBase64Encoded;
    }
}
