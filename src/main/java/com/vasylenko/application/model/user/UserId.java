package com.vasylenko.application.model.user;

import com.vasylenko.application.model.entity.AbstractEntityId;

import java.util.UUID;

public class UserId extends AbstractEntityId<UUID> {

    /**
     * Default constructor for JPA
     */
    protected UserId() {
    }

    public UserId(UUID id) {
        super(id);
    }
}
