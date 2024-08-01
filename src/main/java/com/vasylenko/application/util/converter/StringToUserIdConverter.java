package com.vasylenko.application.util.converter;

import com.vasylenko.application.model.user.UserId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Converter to convert String to UserId.
 */
@Component
public class StringToUserIdConverter implements Converter<String, UserId> {

    /**
     * Converts a String to a UserId.
     *
     * @param str the String to convert
     * @return the converted UserId
     */
    @Override
    public UserId convert(String str) {

        return new UserId(UUID.fromString(str));
    }
}
