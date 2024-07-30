package com.vasylenko.application.model.entity;

import java.io.Serializable;

/**
 * Interface representing an entity ID.
 *
 * @param <T> the type of the ID
 */
public interface EntityId<T> extends Serializable {

    /**
     * Returns the ID.
     *
     * @return the ID
     */
    T getId();

    /**
     * Returns the ID as a string.
     *
     * @return the ID as a string
     */
    String asString();
}
