package com.vasylenko.application.model.entity;

/**
 * Interface representing an entity with an ID.
 *
 * @param <T> the type of {@link EntityId} that will be used for this entity
 */
public interface Entity<T extends EntityId<?>> {

    /**
     * Returns the ID of the entity.
     *
     * @return the entity ID
     */
    T getId();
}
