package com.vasylenko.application.util.converter;

import com.vasylenko.application.model.team.TeamId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Converter to convert String to TeamId.
 */
@Component
public class StringToTeamIdConverter implements Converter<String, TeamId> {

    private static final Logger logger = LoggerFactory.getLogger(StringToTeamIdConverter.class);

    /**
     * Converts a String to a TeamId.
     *
     * @param s the String to convert
     * @return the converted TeamId
     */
    @Override
    public TeamId convert(String s) {
        logger.debug("Converting String to TeamId: {}", s);

        TeamId teamId = new TeamId(UUID.fromString(s));

        logger.info("Converted String to TeamId: {}", teamId);
        return teamId;
    }
}
