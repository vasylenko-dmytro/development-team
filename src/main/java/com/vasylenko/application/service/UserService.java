package com.vasylenko.application.service;

import com.google.common.collect.ImmutableSortedSet;
import com.vasylenko.application.model.Email;
import com.vasylenko.application.model.user.parameters.CreateUserParameters;
import com.vasylenko.application.model.user.parameters.EditUserParameters;
import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserId;
import com.vasylenko.application.model.user.UserNameAndId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    User createUser(CreateUserParameters parameters);
    User createAdministrator(CreateUserParameters parameters);
    User editUser(UserId userId, EditUserParameters parameters);
    Page<User> getUsers(Pageable pageable);
    boolean userWithEmailExists(Email email);
    Optional<User> getUser(UserId userId);
    void deleteUser(UserId userId);
    long countUsers();
    void deleteAllUsers();
    ImmutableSortedSet<UserNameAndId> getAllUsersNameAndId();
}
