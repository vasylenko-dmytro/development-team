package com.vasylenko.application.util.converter;

import com.vasylenko.application.model.PhoneNumber;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA AttributeConverter to convert PhoneNumber objects to String and vice versa.
 */
@Converter(autoApply = true)
public class PhoneNumberAttributeConverter implements AttributeConverter<PhoneNumber, String> {

    /**
     * Converts the PhoneNumber object to its String representation for storing in the database.
     *
     * @param attribute the PhoneNumber object to convert
     * @return the String representation of the PhoneNumber object
     */
    @Override
    public String convertToDatabaseColumn(PhoneNumber attribute) {
        return attribute.asString();
    }

    /**
     * Converts the String representation of the PhoneNumber from the database back to a PhoneNumber object.
     *
     * @param dbData the String representation of the PhoneNumber
     * @return the PhoneNumber object
     */
    @Override
    public PhoneNumber convertToEntityAttribute(String dbData) {
        return new PhoneNumber(dbData);
    }
}
