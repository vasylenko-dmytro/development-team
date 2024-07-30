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

/**
 * Service interface for managing users.
 */
public interface UserService {

    /**
     * Creates a new user with the given parameters.
     *
     * @param parameters the parameters for creating the user
     * @return the created user
     */
    User createUser(CreateUserParameters parameters);

    /**
     * Creates a new administrator with the given parameters.
     *
     * @param parameters the parameters for creating the administrator
     */
    void createAdministrator(CreateUserParameters parameters);

    /**
     * Edits an existing user with the given parameters.
     *
     * @param userId the ID of the user to edit
     * @param parameters the parameters for editing the user
     */
    void editUser(UserId userId, EditUserParameters parameters);

    /**
     * Retrieves a paginated list of users.
     *
     * @param pageable the pagination information
     * @return a page of users
     */
    Page<User> getUsers(Pageable pageable);


    /**
     * Checks if a user with the given email exists.
     *
     * @param email the email to check
     * @return true if a user with the given email exists, false otherwise
     */
    boolean userWithEmailExists(Email email);

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to retrieve
     * @return an optional containing the user if found
     */
    Optional<User> getUser(UserId userId);

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete
     */
    void deleteUser(UserId userId);

    /**
     * Deletes all users.
     */
    void deleteAllUsers();

    /**
     * Retrieves all users' names and IDs.
     *
     * @return a sorted set of users' names and IDs
     */
    ImmutableSortedSet<UserNameAndId> getAllUsersNameAndId();
}
