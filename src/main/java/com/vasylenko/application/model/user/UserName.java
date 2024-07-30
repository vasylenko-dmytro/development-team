package com.vasylenko.application.model.user;

import com.google.common.base.MoreObjects;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.Assert;

/**
 * Class representing a user's name.
 */
@Embeddable
@Getter
@EqualsAndHashCode
public class UserName {
    private String firstName;
    private String lastName;

    protected UserName() {
    }

    public UserName(String firstName, String lastName) {
        Assert.hasText(firstName, "firstName cannot be blank");
        Assert.hasText(lastName, "lastName cannot be blank");
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .toString();
    }
}
