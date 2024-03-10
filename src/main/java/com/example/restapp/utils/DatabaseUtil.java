package com.example.restapp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
    private static final Properties PROPERTIES = loadProperties(); // Метод для загрузки настроек подключения

    static {
        try (InputStream input = DatabaseUtil.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            PROPERTIES.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(PROPERTIES.getProperty("db.url"),
                    PROPERTIES.getProperty("db.username"), PROPERTIES.getProperty("db.password"));
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Could not load database properties file", e);
        }
        return properties;
    }
}
