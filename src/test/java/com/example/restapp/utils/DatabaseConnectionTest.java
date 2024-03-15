package com.example.restapp.utils;

import com.example.restapp.exceptions.ConnectionException;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DatabaseConnectionTest {
    private final String jdbcDriver = "org.postgresql.Driver";
    private final String jdbcUrl = "jdbc:postgresql://localhost:5432/db_app";
    private final String jdbcUser = "postgres";
    private final String jdbcPassword = "12345";
    private final String invalidDriver = "invalidDriver";
    private final String invalidUrl = "invalidUrl";
    private final String invalidUser = "invalidUser";
    private final String invalidPassword = "invalidPassword";

    @Test
    void testSuccessfulConnection() {
        DatabaseConnection dbConnection = new DatabaseConnection(jdbcDriver
                , jdbcUrl, jdbcUser
                , jdbcPassword);
        assertDoesNotThrow(() -> dbConnection.getConnection());
    }

    @Test
    void testFailedConnectionParameters() {
        DatabaseConnection dbConnection = new DatabaseConnection(invalidDriver, invalidUrl, invalidUser, invalidPassword);
        assertThrows(ConnectionException.class, () -> dbConnection.getConnection());
    }

    @Test
    void testDriverNotFound() {
        DatabaseConnection dbConnection = new DatabaseConnection(invalidDriver, jdbcDriver, jdbcUser, jdbcPassword);
        assertThrows(ConnectionException.class, () -> dbConnection.getConnection());
    }

    @Test
    void testConnectionClosing() {
        DatabaseConnection dbConnection = new DatabaseConnection(jdbcDriver, jdbcDriver, jdbcUser, jdbcPassword);
        Connection conn = dbConnection.getConnection();
        assertDoesNotThrow(() -> conn.close());
    }
}
