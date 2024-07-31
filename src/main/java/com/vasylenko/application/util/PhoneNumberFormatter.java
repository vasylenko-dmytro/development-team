package com.vasylenko.application.util;

import com.vasylenko.application.model.PhoneNumber;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

import java.util.Locale;

/**
 * Formatter for converting between String and PhoneNumber.
 */
public class PhoneNumberFormatter implements Formatter<PhoneNumber> {

    private static final Logger logger = LoggerFactory.getLogger(PhoneNumberFormatter.class);

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
        logger.debug("Parsing text to PhoneNumber: {}", text);

        PhoneNumber phoneNumber = new PhoneNumber(text);

        logger.info("Parsed PhoneNumber: {}", phoneNumber);
        return phoneNumber;
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
        logger.debug("Printing PhoneNumber: {}", object);

        String phoneNumberString = object.asString();

        logger.info("Printed PhoneNumber as String: {}", phoneNumberString);
        return phoneNumberString;
    }
}
