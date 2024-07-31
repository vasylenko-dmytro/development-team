package com.vasylenko.application.util.converter;

import com.vasylenko.application.model.PhoneNumber;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JPA AttributeConverter to convert PhoneNumber objects to String and vice versa.
 */
@Converter(autoApply = true)
public class PhoneNumberAttributeConverter implements AttributeConverter<PhoneNumber, String> {

    private static final Logger logger = LoggerFactory.getLogger(PhoneNumberAttributeConverter.class);

    /**
     * Converts the PhoneNumber object to its String representation for storing in the database.
     *
     * @param attribute the PhoneNumber object to convert
     * @return the String representation of the PhoneNumber object
     */
    @Override
    public String convertToDatabaseColumn(PhoneNumber attribute) {
        logger.debug("Converting PhoneNumber object to String: {}", attribute);

        String dbColumn = attribute.asString();

        logger.info("Converted PhoneNumber object to String: {}", dbColumn);
        return dbColumn;
    }

    /**
     * Converts the String representation of the PhoneNumber from the database back to a PhoneNumber object.
     *
     * @param dbData the String representation of the PhoneNumber
     * @return the PhoneNumber object
     */
    @Override
    public PhoneNumber convertToEntityAttribute(String dbData) {
        logger.debug("Converting String to PhoneNumber object: {}", dbData);

        PhoneNumber phoneNumber = new PhoneNumber(dbData);

        logger.info("Converted String to PhoneNumber object: {}", phoneNumber);
        return phoneNumber;
    }
}
