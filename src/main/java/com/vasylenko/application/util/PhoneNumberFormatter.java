package com.vasylenko.application.util;

import com.vasylenko.application.model.PhoneNumber;
import jakarta.annotation.Nonnull;
import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

import java.util.Locale;

public class PhoneNumberFormatter implements Formatter<PhoneNumber> {
    @Nonnull
    @Override
    public PhoneNumber parse(@Nonnull String text, @Nonnull Locale locale) throws ParseException {
        return new PhoneNumber(text);
    }

    @Nonnull
    @Override
    public String print(@Nonnull PhoneNumber object, @Nonnull Locale locale) {
        return object.asString();
    }
}
