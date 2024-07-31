package com.vasylenko.application.repository;

import com.vasylenko.application.model.team.TeamId;
import com.vasylenko.application.model.team.member.TeamMemberId;
import com.vasylenko.application.util.UniqueIdGenerator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Implementation of the TeamRepository interface.
 */
@RequiredArgsConstructor
public class TeamRepositoryImpl {

    private static final Logger logger = LoggerFactory.getLogger(TeamRepositoryImpl.class);

    private final UniqueIdGenerator<UUID> generator;

    /**
     * Generates the next unique TeamId.
     *
     * @return a new unique TeamId
     */
    public TeamId nextId() {
        logger.info("Generating next unique TeamId");
        TeamId teamId = new TeamId(generator.getNextUniqueId());
        logger.info("Generated TeamId: {}", teamId);
        return teamId;
    }

    /**
     * Generates the next unique TeamMemberId.
     *
     * @return a new unique TeamMemberId
     */
    public TeamMemberId nextMemberId() {
        logger.info("Generating next unique TeamMemberId");
        TeamMemberId teamMemberId = new TeamMemberId(generator.getNextUniqueId());
        logger.info("Generated TeamMemberId: {}", teamMemberId);
        return teamMemberId;
    }
}


