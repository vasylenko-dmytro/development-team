package com.vasylenko.application.repository;

import com.vasylenko.application.model.user.UserId;
import com.vasylenko.application.util.UniqueIdGenerator;

import java.util.UUID;

public class UserRepositoryImpl {
    private final UniqueIdGenerator<UUID> generator;

    public UserRepositoryImpl(UniqueIdGenerator<UUID> generator) { //<.>
        this.generator = generator;
    }

    public UserId nextId() {
        return new UserId(generator.getNextUniqueId()); //<.>
    }
}
