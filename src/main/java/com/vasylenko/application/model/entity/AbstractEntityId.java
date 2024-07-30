package com.vasylenko.application.model.entity;

import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;
import java.util.Objects;

/**
 * Abstract base class for entity IDs.
 *
 * @param <T> the type of the ID
 */
@MappedSuperclass
public abstract class AbstractEntityId<T extends Serializable> implements Serializable, EntityId<T> {

    private T id;

    /**
     * Default constructor for JPA.
     */
    protected AbstractEntityId() {
    }

    /**
     * Constructs a new AbstractEntityId with the given ID.
     *
     * @param id the ID of the entity, must not be null
     */
    protected AbstractEntityId(T id) {
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
     * Returns the ID as a string.
     *
     * @return the ID as a string
     */
    @Override
    public String asString() {
        return id.toString();
    }

    /**
     * Checks if this entity ID is equal to another object.
     *
     * @param o the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public final boolean equals(Object o) {
        boolean result = false;

        if (this == o) {
            result = true;
        } else if (o == null) {
            return false;
        } else if (o.getClass().equals(getClass())) {
            AbstractEntityId<?> other = (AbstractEntityId<?>) o;
            result = Objects.equals(getId(), other.getId());
        }

        return result;
    }

    /**
     * Returns the hash code of this entity ID.
     *
     * @return the hash code
     */
    @Override
    public final int hashCode() {
        return Objects.hash(getId());
    }

    /**
     * Returns a string representation of this entity ID.
     *
     * @return a string representation of the entity ID
     */
    @Override
    public String toString() {
        return String.format("%s[id=%s]", getClass().getSimpleName(), getId());
    }
}
