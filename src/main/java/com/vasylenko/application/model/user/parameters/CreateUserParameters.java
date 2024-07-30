package com.vasylenko.application.model.user.parameters;

import com.vasylenko.application.model.Gender;
import com.vasylenko.application.model.Email;
import com.vasylenko.application.model.PhoneNumber;
import com.vasylenko.application.model.user.UserName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

/**
 * Class representing the parameters for creating a user.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CreateUserParameters {
    @NonNull
    private final UserName userName;
    private final String password;
    @NonNull
    private final Gender gender;
    @NonNull
    private final LocalDate birthday;
    @NonNull
    private final Email email;
    @NonNull
    private final PhoneNumber phoneNumber;
    private MultipartFile avatar;

    /**
     * Constructs a new CreateUserParameters with the given details.
     *
     * @param userName the name of the user, must not be null
     * @param password the password of the user
     * @param gender the gender of the user, must not be null
     * @param birthday the birthday of the user, must not be null
     * @param email the email of the user, must not be null
     * @param phoneNumber the phone number of the user, must not be null
     */
    public CreateUserParameters(@NonNull UserName userName,
                                String password,
                                @NonNull Gender gender,
                                @NonNull LocalDate birthday,
                                @NonNull Email email,
                                @NonNull PhoneNumber phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}