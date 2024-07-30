package com.vasylenko.application.util;

import com.vasylenko.application.model.PhoneNumber;
import jakarta.annotation.Nonnull;
import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

import java.util.Locale;

/**
 * Formatter for converting between String and PhoneNumber.
 */
public class PhoneNumberFormatter implements Formatter<PhoneNumber> {

    /**
     * Parses a String to create a PhoneNumber.
     *
     * @param text the text to parse
     * @param locale the locale
     * @return the parsed PhoneNumber
     * @throws ParseException if the text cannot be parsed
     */
    @Nonnull
    @Override
    public PhoneNumber parse(@Nonnull String text, @Nonnull Locale locale) throws ParseException {
        return new PhoneNumber(text);
    }

    /**
     * Prints a PhoneNumber as a String.
     *
     * @param object the PhoneNumber to print
     * @param locale the locale
     * @return the string representation of the PhoneNumber
     */
    @Nonnull
    @Override
    public String print(@Nonnull PhoneNumber object, @Nonnull Locale locale) {
        return object.asString();
    }
}
