package com.example.restapp.utils;

import com.example.restapp.exceptions.PropertiesException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseUtil {
    private final static Properties propertiesUtil = new Properties();

    private DatabaseUtil() {
    }

    static {
        try (InputStream input = DatabaseUtil.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new PropertiesException("application.properties not found");
            }
            propertiesUtil.load(input);
        } catch (IOException e) {
            throw new PropertiesException("Stream was not closed");
        }
    }

    public static String getDbDriver() {
        return propertiesUtil.getProperty("db.driver");
    }

    public static String getDbUrl() {
        return propertiesUtil.getProperty("db.url");
    }

    public static String getDbUsername() {
        return propertiesUtil.getProperty("db.username");
    }

    public static String getDbPassword() {
        return propertiesUtil.getProperty("db.password");
    }
}