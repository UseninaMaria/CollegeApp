package com.example.restapp.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NotFoundExceptionTest {

    @Test
    void testConstructorsWithMessage() {
        String errorMessage = "Not found";
        NotFoundException exception = new NotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testConstructorsWithMessageAndCause() {
        String errorMessage = "Not found";
        Throwable cause = new IllegalArgumentException("Invalid argument");
        NotFoundException exception = new NotFoundException(errorMessage, cause);
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testConstructorsWithCause() {
        Throwable cause = new IllegalArgumentException("Invalid argument");
        NotFoundException exception = new NotFoundException(cause);
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testDefaultConstructor() {
        NotFoundException exception = new NotFoundException();
        assertNull(exception.getMessage());
    }

    @Test
    void testConstructorsWithMessageAndCauseAndFlags() {
        String errorMessage = "Not found";
        Throwable cause = new IllegalArgumentException("Invalid argument");
        NotFoundException exception = new NotFoundException(errorMessage, cause, true, false);
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
