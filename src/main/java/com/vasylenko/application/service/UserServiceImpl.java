package com.vasylenko.application.service;

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
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a new user with the given parameters.
     *
     * @param parameters the parameters for creating the user
     * @return the created user
     */
    @Override
    public User createUser(CreateUserParameters parameters) {
        logger.debug("Creating user {} ({})", parameters.getUserName().getFullName(), parameters.getEmail().asString());

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

        logger.info("User {} ({}) created successfully", parameters.getUserName().getFullName(), parameters.getEmail().asString());
        return repository.save(user);
    }

    /**
     * Creates a new administrator with the given parameters.
     *
     * @param parameters the parameters for creating the administrator
     */
    @Override
    public void createAdministrator(CreateUserParameters parameters) {
        logger.debug("Creating administrator {} ({})", parameters.getUserName().getFullName(), parameters.getEmail().asString());

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

        logger.info("Administrator {} ({}) created successfully", parameters.getUserName().getFullName(), parameters.getEmail().asString());
    }

    /**
     * Edits an existing user with the given parameters.
     *
     * @param userId the ID of the user to edit
     * @param parameters the parameters for editing the user
     */
    @Override
    public void editUser(UserId userId, EditUserParameters parameters) {
        logger.debug("Editing user with ID: {}", userId);

        User user = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        if (parameters.getVersion() != user.getVersion()) {
            logger.warn("Version conflict for user with ID: {}", userId);
            throw new ObjectOptimisticLockingFailureException(User.class, user.getId().asString());
        }
        parameters.update(user);

        logger.info("User with ID: {} edited successfully", userId);
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
        logger.info("Retrieving paginated list of users with pagination: {}", pageable);

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
        logger.info("Checking if user with email {} exists", email.asString());

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
        logger.info("Retrieving user with ID: {}", userId);

        return repository.findById(userId);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete
     */
    @Override
    public void deleteUser(UserId userId) {
        logger.info("Deleting user with ID: {}", userId);

        repository.deleteById(userId);

        logger.info("User with ID: {} deleted successfully", userId);
    }

    /**
     * Deletes all users.
     */
    @Override
    public void deleteAllUsers() {
        logger.info("Deleting all users");

        repository.deleteAll();

        logger.info("All users deleted successfully");
    }

    /**
     * Retrieves all users' names and IDs.
     *
     * @return a sorted set of users' names and IDs
     */
    @Override
    public ImmutableSortedSet<UserNameAndId> getAllUsersNameAndId() {
        logger.info("Retrieving all users' names and IDs");

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
                logger.info("Avatar stored for user {}", user.getId());
            } catch (IOException e) {
                logger.error("Error storing avatar for user {}", user.getId(), e);
                throw new UserServiceException(e);
            }
        }
    }
}
