package com.vasylenko.application.util.converter;

import com.vasylenko.application.model.Email;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JPA AttributeConverter to convert Email objects to String and vice versa.
 */
@Converter(autoApply = true)
public class EmailAttributeConverter implements AttributeConverter<Email, String> {

    private static final Logger logger = LoggerFactory.getLogger(EmailAttributeConverter.class);

    /**
     * Converts the Email object to its String representation for storing in the database.
     *
     * @param attribute the Email object to convert
     * @return the String representation of the Email object
     */
    @Override
    public String convertToDatabaseColumn(Email attribute) {
        logger.debug("Converting Email object to String: {}", attribute);

        String dbColumn = attribute.asString();

        logger.info("Converted Email object to String: {}", dbColumn);
        return dbColumn;
    }

    /**
     * Converts the String representation of the Email from the database back to an Email object.
     *
     * @param dbData the String representation of the Email
     * @return the Email object
     */
    @Override
    public Email convertToEntityAttribute(String dbData) {
        logger.debug("Converting String to Email object: {}", dbData);

        Email email = new Email(dbData);

        logger.info("Converted String to Email object: {}", email);
        return email;
    }
}
