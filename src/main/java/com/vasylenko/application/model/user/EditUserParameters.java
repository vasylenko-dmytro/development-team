package com.vasylenko.application.model.user;

import com.vasylenko.application.model.Gender;
import com.vasylenko.application.model.email.Email;
import com.vasylenko.application.model.phone.PhoneNumber;

import java.time.LocalDate;

public class EditUserParameters extends CreateUserParameters {
    private final long version;
    public EditUserParameters(long version, UserName userName, Gender gender, LocalDate birthday, Email email, PhoneNumber phoneNumber) {
        super(userName, gender, birthday, email, phoneNumber);
        this.version = version;
    }
    public long getVersion() {
        return version;
    }

    public void update(User user) {
        user.setUserName(getUserName());
        user.setGender(getGender());
        user.setBirthday(getBirthday());
        user.setEmail(getEmail());
        user.setPhoneNumber(getPhoneNumber());
    }
}
