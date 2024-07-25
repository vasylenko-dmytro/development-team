package com.vasylenko.application.service;

import com.vasylenko.application.model.email.Email;
import com.vasylenko.application.model.user.CreateUserParameters;
import com.vasylenko.application.model.user.EditUserParameters;
import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    User createUser(CreateUserParameters parameters);
    User createAdministrator(CreateUserParameters parameters);
    Page<User> getUsers(Pageable pageable);
    boolean userWithEmailExists(Email email);
    User editUser(UserId userId, EditUserParameters parameters);
    Optional<User> getUser(UserId userId);
    void deleteUser(UserId userId);
}
