package com.vasylenko.application.util.converter;

import com.vasylenko.application.model.Email;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

/**
 * JPA AttributeConverter to convert Email objects to String and vice versa.
 */
@Component
@Converter(autoApply = true)
public class EmailAttributeConverter implements AttributeConverter<Email, String> {

    /**
     * Converts the Email object to its String representation for storing in the database.
     *
     * @param attribute the Email object to convert
     * @return the String representation of the Email object
     */
    @Override
    public String convertToDatabaseColumn(Email attribute) {
        return attribute.asString();
    }

    /**
     * Converts the String representation of the Email from the database back to an Email object.
     *
     * @param dbData the String representation of the Email
     * @return the Email object
     */
    @Override
    public Email convertToEntityAttribute(String dbData) {

        return new Email(dbData);
    }
}
