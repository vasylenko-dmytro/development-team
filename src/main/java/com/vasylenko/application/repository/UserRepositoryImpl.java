package com.vasylenko.application.repository;

import com.vasylenko.application.model.user.UserId;
import com.vasylenko.application.util.UniqueIdGenerator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Implementation of the UserRepository interface.
 */
@RequiredArgsConstructor
public class UserRepositoryImpl {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private final UniqueIdGenerator<UUID> generator;

    /**
     * Generates the next unique UserId.
     *
     * @return a new unique UserId
     */
    public UserId nextId() {
        logger.info("Generating next unique UserId");
        UserId userId = new UserId(generator.getNextUniqueId());
        logger.info("Generated UserId: {}", userId);
        return userId;
    }
}
