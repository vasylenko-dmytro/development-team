package com.vasylenko.application.model.user.parameters;

import com.vasylenko.application.exception.UserServiceException;
import com.vasylenko.application.model.Gender;
import com.vasylenko.application.model.Email;
import com.vasylenko.application.model.PhoneNumber;
import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserName;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Class representing the parameters for editing a user.
 */
@Getter
public class EditUserParameters extends CreateUserParameters {

    private final long version;

    /**
     * Constructs a new EditUserParameters with the given details.
     *
     * @param version the version of the user
     * @param userName the name of the user, must not be null
     * @param gender the gender of the user, must not be null
     * @param birthday the birthday of the user, must not be null
     * @param email the email of the user, must not be null
     * @param phoneNumber the phone number of the user, must not be null
     */
    public EditUserParameters(long version, UserName userName, Gender gender, LocalDate birthday, Email email, PhoneNumber phoneNumber) {
        super(userName, null, gender, birthday, email, phoneNumber);
        this.version = version;
    }

    /**
     * Updates the given user with the parameters.
     *
     * @param user the user to update
     */
    public void update(User user) {
        user.setUserName(getUserName());
        user.setGender(getGender());
        user.setBirthday(getBirthday());
        user.setEmail(getEmail());
        user.setPhoneNumber(getPhoneNumber());

        MultipartFile avatar = getAvatar();
        if (avatar != null) {
            try {
                user.setAvatar(avatar.getBytes());
            } catch (IOException e) {
                throw new UserServiceException(e);
            }
        }
    }
}
