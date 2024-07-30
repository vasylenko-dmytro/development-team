package com.vasylenko.application.repository;

import com.vasylenko.application.model.team.TeamId;
import com.vasylenko.application.model.team.member.TeamMemberId;
import com.vasylenko.application.util.UniqueIdGenerator;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * Implementation of the TeamRepository interface.
 */
@RequiredArgsConstructor
public class TeamRepositoryImpl {

    private final UniqueIdGenerator<UUID> generator;

    /**
     * Generates the next unique TeamId.
     *
     * @return a new unique TeamId
     */
    public TeamId nextId() {
        return new TeamId(generator.getNextUniqueId());
    }

    /**
     * Generates the next unique TeamMemberId.
     *
     * @return a new unique TeamMemberId
     */
    public TeamMemberId nextMemberId() {
        return new TeamMemberId(generator.getNextUniqueId());
    }
}


