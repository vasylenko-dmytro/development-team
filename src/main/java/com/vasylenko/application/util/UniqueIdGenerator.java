package com.vasylenko.application.util;

/**
 * Interface that allows to generate unique id's of a given type.
 *
 * @param <T> the type of id's to generate
 */
public interface UniqueIdGenerator<T> {
    T getNextUniqueId();
}
