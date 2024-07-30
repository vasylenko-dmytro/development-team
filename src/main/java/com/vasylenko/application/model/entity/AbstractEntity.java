package com.vasylenko.application.model.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

/**
 * Abstract base class for entities with an ID.
 *
 * @param <T> the type of {@link EntityId} that will be used for this entity
 */
@MappedSuperclass
public abstract class AbstractEntity<T extends EntityId<?>> implements Entity<T> {

    @EmbeddedId
    private T id;

    /**
     * Default constructor for JPA.
     */
    protected AbstractEntity() {
    }

    /**
     * Constructs a new AbstractEntity with the given ID.
     *
     * @param id the ID of the entity, must not be null
     */
    public AbstractEntity(T id) {
        this.id = Objects.requireNonNull(id, "id should not be null");
    }

    /**
     * Returns the ID of the entity.
     *
     * @return the entity ID
     */
    @Override
    public T getId() {
        return id;
    }


    /**
     * Checks if this entity is equal to another object.
     *
     * @param obj the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public final boolean equals(Object obj) {
        boolean result = false;

        if (this == obj) {
            result = true;
        } else if (obj == null) {
            return false;
        } else if (obj.getClass().equals(getClass())) {
            AbstractEntity<?> other = (AbstractEntity<?>) obj;
            result = Objects.equals(getId(), other.getId());
        }

        return result;
    }

    /**
     * Returns the hash code of this entity.
     *
     * @return the hash code
     */
    @Override
    public final int hashCode() {
        return Objects.hash(getId());
    }

    /**
     * Returns a string representation of this entity.
     *
     * @return a string representation of the entity
     */
    @Override
    public String toString() {
        return String.format("%s[id=%s]", getClass().getSimpleName(), getId());
    }
}