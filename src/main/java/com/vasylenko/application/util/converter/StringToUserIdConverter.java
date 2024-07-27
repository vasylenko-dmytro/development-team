package com.vasylenko.application.util.converter;

import com.vasylenko.application.model.user.UserId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringToUserIdConverter implements Converter<String, UserId> {
    @Override
    public UserId convert(String str) {
        return new UserId(UUID.fromString(str));
    }
}
