package com.vasylenko.application.model.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Abstract base class for entities that want to keep track of the version of the entity
 * for optimistic locking purposes.
 *
 * @param <T> the type of {@link EntityId} that will be used for this entity
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractVersionedEntity<T extends EntityId<?>> extends AbstractEntity<T> {

    @Version
    private long version;

    protected AbstractVersionedEntity() {
    }

    public AbstractVersionedEntity(T id) {
        super(id);
    }

    public long getVersion() {
        return version;
    }
}

