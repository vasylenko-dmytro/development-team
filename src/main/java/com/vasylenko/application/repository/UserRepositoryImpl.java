package com.vasylenko.application.repository;

import com.vasylenko.application.model.user.UserId;
import com.vasylenko.application.util.UniqueIdGenerator;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * Implementation of the UserRepository interface.
 */
@RequiredArgsConstructor
public class UserRepositoryImpl {

    private final UniqueIdGenerator<UUID> generator;

    /**
     * Generates the next unique UserId.
     *
     * @return a new unique UserId
     */
    public UserId nextId() {
        return new UserId(generator.getNextUniqueId());
    }
}
