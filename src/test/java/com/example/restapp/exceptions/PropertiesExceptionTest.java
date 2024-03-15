package com.example.restapp.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertiesExceptionTest {

    @Test
    void testPropertiesExceptionWithMessage() {
        String message = "Error in properties file";

        PropertiesException exception = new PropertiesException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    void testPropertiesExceptionWithMessageAndCause() {
        String message = "Error in properties file";
        Throwable cause = new Throwable("Cause");

        PropertiesException exception = new PropertiesException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testPropertiesExceptionWithCause() {
        Throwable cause = new Throwable("Cause");

        PropertiesException exception = new PropertiesException(cause);

        assertEquals(cause, exception.getCause());
    }
}
