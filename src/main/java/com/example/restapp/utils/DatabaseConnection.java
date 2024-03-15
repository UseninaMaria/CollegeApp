package com.example.restapp.utils;

import com.example.restapp.exceptions.ConnectionException;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@AllArgsConstructor
public class DatabaseConnection {
    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPassword;

    public Connection getConnection() {
        try {
            Class.forName(jdbcDriver);
            return DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ConnectionException("Соединение не установлено");
        }
    }
}

