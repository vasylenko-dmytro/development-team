package com.vasylenko.application.model;

public enum Gender {
    MALE,
    FEMALE,
    OTHER,
    UNKNOWN;

    /**
     * Converts a string to a Gender.
     *
     * @param gender the string representation of the gender
     * @return the corresponding Gender
     * @throws IllegalArgumentException if the gender is not valid
     */
    public static Gender fromString(String gender) {
        for (Gender g : Gender.values()) {
            if (g.name().equalsIgnoreCase(gender)) {
                return g;
            }
        }
        throw new IllegalArgumentException("Unknown gender: " + gender);
    }
}
