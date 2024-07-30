package com.vasylenko.application.util;

/**
 * Interface that allows to generate unique id's of a given type.
 *
 * @param <T> the type of id's to generate
 */
public interface UniqueIdGenerator<T> {

    /**
     * Generates the next unique ID.
     *
     * @return a new unique ID of type T
     */
    T getNextUniqueId();
}
