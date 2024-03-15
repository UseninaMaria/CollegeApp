package com.example.restapp.utils;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseUtilTest {

    @Test
    public void testPropertiesLoading() {
        assertNotNull(DatabaseUtil.getDbDriver());
        assertNotNull(DatabaseUtil.getDbUrl());
        assertNotNull(DatabaseUtil.getDbUsername());
        assertNotNull(DatabaseUtil.getDbPassword());
    }

    @Test
    public void testGetDbDriver() {
        assertEquals("org.postgresql.Driver", DatabaseUtil.getDbDriver());
    }

    @Test
    public void testGetDbUrl() {
        assertEquals("jdbc:postgresql://localhost:5432/db_app", DatabaseUtil.getDbUrl());
    }

    @Test
    void testGetDbUsername() {
        assertEquals("postgres", DatabaseUtil.getDbUsername());
    }

    @Test
    void testGetDbPassword() {
        assertEquals("12345", DatabaseUtil.getDbPassword());
    }
}
