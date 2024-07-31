package com.vasylenko.application.util.converter;

import com.vasylenko.application.model.user.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Converter to convert String to UserId.
 */
@Component
public class StringToUserIdConverter implements Converter<String, UserId> {

    private static final Logger logger = LoggerFactory.getLogger(StringToUserIdConverter.class);

    /**
     * Converts a String to a UserId.
     *
     * @param str the String to convert
     * @return the converted UserId
     */
    @Override
    public UserId convert(String str) {
        logger.debug("Converting String to UserId: {}", str);

        UserId userId = new UserId(UUID.fromString(str));

        logger.info("Converted String to UserId: {}", userId);
        return userId;
    }
}
