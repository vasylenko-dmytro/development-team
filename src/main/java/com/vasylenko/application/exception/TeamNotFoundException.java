package com.vasylenko.application.exception;

import com.vasylenko.application.model.team.TeamId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a team is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TeamNotFoundException extends RuntimeException {

    /**
     * Constructs a new TeamNotFoundException with the specified team ID.
     *
     * @param teamId the ID of the team that was not found
     */
    public TeamNotFoundException(TeamId teamId) {
        super(String.format("Team with id %s not found", teamId.asString()));
    }
}
