package com.example.restapp.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConnectionExceptionTest {

    @Test
    void testConnectionExceptionWithMessage() {
        String message = "Connection failed";

        ConnectionException exception = new ConnectionException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    void testConnectionExceptionWithMessageAndCause() {
        String message = "Connection failed";
        Throwable cause = new Throwable("Underlying cause");

        ConnectionException exception = new ConnectionException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testConnectionExceptionWithCause() {
        Throwable cause = new Throwable("Underlying cause");

        ConnectionException exception = new ConnectionException(cause);

        assertEquals(cause, exception.getCause());
    }
}
