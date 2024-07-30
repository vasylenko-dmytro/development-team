package com.vasylenko.application.model;

import com.google.common.base.MoreObjects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Represents a phone number with validation.
 */
@Getter
@EqualsAndHashCode
public class PhoneNumber {

    private static final Pattern VALIDATION_PATTERN = Pattern.compile("[0-9.\\-() x/+]+");

    private final String phoneNumber;

    /**
     * Protected constructor for JPA.
     */
    protected PhoneNumber() {
        this.phoneNumber = null;
    }

    /**
     * Constructs a PhoneNumber with validation.
     *
     * @param phoneNumber the phone number string
     * @throws IllegalArgumentException if the phone number is blank or has an invalid format
     */
    public PhoneNumber(String phoneNumber) {
        Assert.hasText(phoneNumber, "phoneNumber cannot be blank");
        Assert.isTrue(VALIDATION_PATTERN.asPredicate().test(phoneNumber), "phoneNumber does not have proper format");
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the phone number as a string.
     *
     * @return the phone number string
     */
    public String asString() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("phoneNumber", phoneNumber)
                .toString();
    }
}
