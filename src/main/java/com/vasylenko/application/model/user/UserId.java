package com.vasylenko.application.model.user;

import com.vasylenko.application.model.entity.AbstractEntityId;

import java.util.UUID;

/**
 * Class representing a user ID.
 */
public class UserId extends AbstractEntityId<UUID> {

    /**
     * Default constructor for JPA.
     */
    protected UserId() {
    }

    /**
     * Constructs a new UserId with the given UUID.
     *
     * @param id the UUID of the user, must not be null
     */
    public UserId(UUID id) {
        super(id);
    }
}
