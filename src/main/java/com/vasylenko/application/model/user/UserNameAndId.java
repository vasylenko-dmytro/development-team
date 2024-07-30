package com.vasylenko.application.model.user;

import jakarta.validation.constraints.NotNull;

/**
 * Class representing a user's ID and name.
 */
public record UserNameAndId(
        @NotNull UserId id,
        @NotNull UserName userName) {
    }