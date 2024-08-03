package com.vasylenko.application.service.impl;

import com.google.common.collect.ImmutableSortedSet;
import com.vasylenko.application.exception.UserNotFoundException;
import com.vasylenko.application.exception.UserServiceException;
import com.vasylenko.application.model.Email;
import com.vasylenko.application.model.user.parameters.CreateUserParameters;
import com.vasylenko.application.model.user.parameters.EditUserParameters;
import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserId;
import com.vasylenko.application.model.user.UserNameAndId;
import com.vasylenko.application.repository.UserRepository;
import com.vasylenko.application.service.UserService;
import com.vasylenko.application.util.CustomLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service implementation for managing users.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final CustomLogger customLogger;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, CustomLogger customLogger) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.customLogger = customLogger;
    }

    /**
     * Creates a new user with the given parameters.
     *
     * @param parameters the parameters for creating the user
     * @return the created user
     */
    @Override
    public User createUser(CreateUserParameters parameters) {
        customLogger.debug(String.format("Creating user %s (%s)",
                parameters.getUserName().getFullName(), parameters.getEmail().asString()));

        UserId userId = repository.nextId();
        String encodedPassword = passwordEncoder.encode(parameters.getPassword());
        User user = User.createUser(userId,
                parameters.getUserName(),
                encodedPassword,
                parameters.getGender(),
                parameters.getBirthday(),
                parameters.getEmail(),
                parameters.getPhoneNumber());
        storeAvatarIfPresent(parameters, user);

        customLogger.info(String.format("User %s (%s) created successfully",
                parameters.getUserName().getFullName(), parameters.getEmail().asString()));
        return repository.save(user);
    }

    /**
     * Creates a new administrator with the given parameters.
     *
     * @param parameters the parameters for creating the administrator
     */
    @Override
    public void createAdministrator(CreateUserParameters parameters) {
        customLogger.debug(String.format("Creating administrator %s (%s)",
                parameters.getUserName().getFullName(), parameters.getEmail().asString()));

        UserId userId = repository.nextId();
        User user = User.createAdministrator(userId,
                parameters.getUserName(),
                passwordEncoder.encode(parameters.getPassword()),
                parameters.getGender(),
                parameters.getBirthday(),
                parameters.getEmail(),
                parameters.getPhoneNumber());
        storeAvatarIfPresent(parameters, user);
        repository.save(user);

        customLogger.info(String.format("Administrator %s (%s) created successfully",
                parameters.getUserName().getFullName(), parameters.getEmail().asString()));
    }

    /**
     * Edits an existing user with the given parameters.
     *
     * @param userId the ID of the user to edit
     * @param parameters the parameters for editing the user
     */
    @Override
    public void editUser(UserId userId, EditUserParameters parameters) {
        customLogger.debug(String.format("Editing user with ID: %s", userId));

        User user = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        if (parameters.getVersion() != user.getVersion()) {
            customLogger.warn(String.format("Version conflict for user with ID: %s", userId));
            throw new ObjectOptimisticLockingFailureException(User.class, user.getId().asString());
        }
        parameters.update(user);

        customLogger.info(String.format("User with ID: %s edited successfully", userId));
    }

    /**
     * Retrieves a paginated list of users.
     *
     * @param pageable the pagination information
     * @return a page of users
     */
    @Override
    @Transactional(readOnly = true)
    public Page<User> getUsers(Pageable pageable) {
        customLogger.info(String.format("Retrieving paginated list of users with pagination: %s", pageable));

        return repository.findAll(pageable);
    }

    /**
     * Checks if a user with the given email exists.
     *
     * @param email the email to check
     * @return true if a user with the given email exists, false otherwise
     */
    @Override
    @Transactional(readOnly = true)
    public boolean userWithEmailExists(Email email) {
        customLogger.info(String.format("Checking if user with email %s exists", email.asString()));

        return repository.existsByEmail(email);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to retrieve
     * @return an optional containing the user if found
     */
    @Override
    public Optional<User> getUser(UserId userId) {
        customLogger.info(String.format("Retrieving user with ID: %s", userId));

        return repository.findById(userId);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete
     */
    @Override
    public void deleteUser(UserId userId) {
        customLogger.info(String.format("Deleting user with ID: %s", userId));

        repository.deleteById(userId);

        customLogger.info(String.format("User with ID: %s deleted successfully", userId));
    }

    /**
     * Deletes all users.
     */
    @Override
    public void deleteAllUsers() {
        customLogger.info("Deleting all users");

        repository.deleteAll();

        customLogger.info("All users deleted successfully");
    }

    /**
     * Retrieves all users' names and IDs.
     *
     * @return a sorted set of users' names and IDs
     */
    @Override
    public ImmutableSortedSet<UserNameAndId> getAllUsersNameAndId() {
        customLogger.info("Retrieving all users' names and IDs");

        Iterable<User> users = repository.findAll();
        return ImmutableSortedSet.copyOf(
                Comparator.comparing(userNameAndId ->
                        userNameAndId.userName().getFullName()),
                StreamSupport.stream(users.spliterator(), false)
                        .map(user -> new UserNameAndId(user.getId(),
                                user.getUserName()))
                        .sorted(Comparator.comparing(userNameAndId ->
                                userNameAndId.userName().getFullName()))
                        .collect(Collectors.toList()));
    }

    /**
     * Stores the avatar if present in the parameters.
     *
     * @param parameters the parameters containing the avatar
     * @param user the user to store the avatar for
     */
    private void storeAvatarIfPresent(CreateUserParameters parameters, User user) {
        MultipartFile avatar = parameters.getAvatar();
        if (avatar != null) {
            try {
                user.setAvatar(avatar.getBytes());
                customLogger.info(String.format("Avatar stored for user %s", user.getId()));
            } catch (IOException e) {
                customLogger.error(String.format("Error storing avatar for user %s", user.getId()));
                throw new UserServiceException(e);
            }
        }
    }
}
