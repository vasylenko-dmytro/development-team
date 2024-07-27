package com.vasylenko.application.model.team;

import com.vasylenko.application.model.entity.AbstractEntityId;

import java.util.UUID;

public class TeamId extends AbstractEntityId<UUID> {

    /**
     * Default constructor for JPA
     */
    protected TeamId() {
    }

    public TeamId(UUID id) {
        super(id);
    }
}
