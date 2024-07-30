package com.vasylenko.application.model.team;

import com.vasylenko.application.model.entity.AbstractEntityId;

import java.util.UUID;

/**
 * Class representing a team ID.
 */
public class TeamId extends AbstractEntityId<UUID> {

    /**
     * Default constructor for JPA.
     */
    protected TeamId() {
    }

    /**
     * Constructs a new TeamId with the given UUID.
     *
     * @param id the UUID of the team, must not be null
     */
    public TeamId(UUID id) {
        super(id);
    }
}
