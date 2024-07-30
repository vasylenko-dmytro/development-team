package com.vasylenko.application.formdata;

import com.vasylenko.application.model.Gender;
import com.vasylenko.application.model.PhoneNumber;
import com.vasylenko.application.model.user.UserRole;
import com.vasylenko.application.validation.NotExistingUser;
import com.vasylenko.application.validation.ValidationGroupOne;
import com.vasylenko.application.validation.ValidationGroupTwo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

/**
 * Base form data for user-related operations.
 */
@Data
@NotExistingUser(groups = ValidationGroupTwo.class)
public class BaseUserFormData {
    @NotNull
    private UserRole userRole;

    @NotBlank
    @Size(min = 1, max = 200, groups = ValidationGroupOne.class)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 200, groups = ValidationGroupOne.class)
    private String lastName;

    @NotNull
    private Gender gender;

    @NotBlank
    @Email(groups = ValidationGroupOne.class)
    private String email;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotNull
    private PhoneNumber phoneNumber;

    private MultipartFile avatarFile;
}
