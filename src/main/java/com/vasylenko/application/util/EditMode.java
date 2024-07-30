package com.vasylenko.application.util;

/**
 * Enum representing the edit modes.
 */
public enum EditMode {
    CREATE, UPDATE;

    /**
     * Converts a string to an EditMode.
     *
     * @param mode the string representation of the mode
     * @return the corresponding EditMode
     * @throws IllegalArgumentException if the mode is not valid
     */
    public static EditMode fromString(String mode) {
        for (EditMode editMode : EditMode.values()) {
            if (editMode.name().equalsIgnoreCase(mode)) {
                return editMode;
            }
        }
        throw new IllegalArgumentException("Unknown edit mode: " + mode);
    }
}
