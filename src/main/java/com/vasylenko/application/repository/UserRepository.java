package com.vasylenko.application.repository;

import com.vasylenko.application.model.Email;
import com.vasylenko.application.model.user.User;
import com.vasylenko.application.model.user.UserId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository interface for User entities.
 */
@Transactional(readOnly = true)
public interface UserRepository extends CrudRepository<User, UserId>, PagingAndSortingRepository<User, UserId> {

    /**
     * Generates the next unique User ID.
     *
     * @return the next User ID
     */
    UserId nextId();

    /**
     * Checks if a user exists by email.
     *
     * @param email the email to check
     * @return true if a user exists with the given email, false otherwise
     */
    boolean existsByEmail(Email email);

    /**
     * Finds a user by email.
     *
     * @param email the email to search for
     * @return an optional containing the user if found
     */
    Optional<User> findByEmail(Email email);
}
