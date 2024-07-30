package com.vasylenko.application.model;

import com.google.common.base.MoreObjects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * Represents an email address with validation.
 */
@Getter
@EqualsAndHashCode
public class Email {

    private final String email;

    /**
     * Protected constructor for JPA.
     */
    protected Email() {
        this.email = null;
    }

    /**
     * Constructs an Email with validation.
     *
     * @param email the email address string
     * @throws IllegalArgumentException if the email is blank or does not contain '@' symbol
     */
    public Email(String email) {
        Assert.hasText(email, "email cannot be blank");
        Assert.isTrue(email.contains("@"), "email should contain @ symbol");
        this.email = email;
    }

    /**
     * Returns the email address as a string.
     *
     * @return the email address string
     */
    public String asString() {
        return email;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("email", email)
                .toString();
    }
}
