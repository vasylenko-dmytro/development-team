package com.vasylenko.application.repository;

import com.vasylenko.application.model.team.TeamId;
import com.vasylenko.application.model.team.member.TeamMemberId;
import com.vasylenko.application.util.UniqueIdGenerator;

import java.util.UUID;

public class TeamRepositoryImpl {
    private final UniqueIdGenerator<UUID> generator;

    public TeamRepositoryImpl(UniqueIdGenerator<UUID> generator) {
        this.generator = generator;
    }

    public TeamId nextId() {
        return new TeamId(generator.getNextUniqueId());
    }

    public TeamMemberId nextMemberId() {
        return new TeamMemberId(generator.getNextUniqueId());
    }
}
