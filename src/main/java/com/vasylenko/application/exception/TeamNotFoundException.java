package com.vasylenko.application.exception;

import com.vasylenko.application.model.team.TeamId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(TeamId teamId) {
        super(String.format("Team with id %s not found", teamId.asString()));
    }
}
