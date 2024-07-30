package com.vasylenko.application.model.team.member;

import com.vasylenko.application.model.entity.AbstractEntityId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * Class representing the ID of a team member.
 */
@Getter
@RequiredArgsConstructor
public class TeamMemberId extends AbstractEntityId<UUID> {

    private final UUID id;

    /**
     * Protected default constructor for JPA.
     */
    protected TeamMemberId() {
        this.id = null;
    }
}
