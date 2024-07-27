package com.vasylenko.application.model.team.member;

import com.vasylenko.application.model.entity.AbstractEntityId;

import java.util.UUID;

public class TeamMemberId extends AbstractEntityId<UUID> {

    /**
     * Default constructor for JPA
     */
    protected TeamMemberId() {
    }

    public TeamMemberId(UUID id) {
        super(id);
    }
}
