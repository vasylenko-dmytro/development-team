package com.vasylenko.application.formdata;

import com.vasylenko.application.model.email.Email;
import com.vasylenko.application.model.phone.PhoneNumber;
import com.vasylenko.application.model.user.EditUserParameters;
import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserName;

public class EditUserFormData extends CreateUserFormData {
    private String id;
    private long version;
    public static EditUserFormData fromUser(User user) {
        EditUserFormData result = new EditUserFormData();
        result.setId(user.getId().asString());
        result.setVersion(user.getVersion());
        result.setFirstName(user.getUserName().getFirstName());
        result.setLastName(user.getUserName().getLastName());
        result.setGender(user.getGender());
        result.setBirthday(user.getBirthday());
        result.setEmail(user.getEmail().asString());
        result.setPhoneNumber(user.getPhoneNumber().asString());
        return result;
    }
    public EditUserParameters toParameters() {
        return new EditUserParameters(version,
                new UserName(getFirstName(), getLastName()),
                getGender(),
                getBirthday(),
                new Email(getEmail()),
                new PhoneNumber(getPhoneNumber()));
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
}
