package com.vasylenko.application.model.user;

import com.vasylenko.application.model.entity.AbstractEntity;
import com.vasylenko.application.model.Gender;
import com.vasylenko.application.model.Email;
import com.vasylenko.application.model.PhoneNumber;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

/**
 * Class representing a user.
 */
@Entity
@Table(name = "tt_user")
@Getter
@Setter
public class User extends AbstractEntity<UserId> {

    @ElementCollection(targetClass = UserRole.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles")
    @Column(name = "role")
    private Set<UserRole> roles;

    @NotNull
    private String password;

    @NotNull
    private UserName userName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    private LocalDate birthday;

    @NotNull
    private Email email;

    @NotNull
    private PhoneNumber phoneNumber;

    private byte[] avatar;

    @Version
    private long version;

    protected User() {
    }

    private User(UserId id,
                 Set<UserRole> roles,
                 UserName userName,
                 String password,
                 Gender gender,
                 LocalDate birthday,
                 Email email,
                 PhoneNumber phoneNumber) {
        super(id);
        this.roles = roles;
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public static User createUser(UserId id,
                                  UserName userName,
                                  String encodedPassword,
                                  Gender gender,
                                  LocalDate birthday,
                                  Email email,
                                  PhoneNumber phoneNumber) {
        return new User(id, Set.of(UserRole.USER), userName, encodedPassword, gender, birthday, email, phoneNumber);
    }

    public static User createAdministrator(UserId id,
                                           UserName userName,
                                           String encodedPassword,
                                           Gender gender,
                                           LocalDate birthday,
                                           Email email,
                                           PhoneNumber phoneNumber) {
        return new User(id, Set.of(UserRole.ADMIN), userName, encodedPassword, gender, birthday, email, phoneNumber);
    }
}