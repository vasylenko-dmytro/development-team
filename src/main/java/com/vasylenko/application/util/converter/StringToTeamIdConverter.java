package com.vasylenko.application.util.converter;

import com.vasylenko.application.model.team.TeamId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringToTeamIdConverter implements Converter<String, TeamId> {
    @Override
    public TeamId convert(String s) {
        return new TeamId(UUID.fromString(s));
    }
}
