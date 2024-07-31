package com.vasylenko.application.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * A {@link UniqueIdGenerator} implementation that generates unique {@link UUID} instances.
 */
public class InMemoryUniqueIdGenerator implements UniqueIdGenerator<UUID> {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryUniqueIdGenerator.class);

    /**
     * Generates the next unique UUID.
     *
     * @return a new unique UUID
     */
    @Override
    public UUID getNextUniqueId() {
        logger.debug("Generating a new unique UUID");

        UUID uniqueId = UUID.randomUUID();

        logger.info("Generated unique UUID: {}", uniqueId);
        return uniqueId;
    }
}
