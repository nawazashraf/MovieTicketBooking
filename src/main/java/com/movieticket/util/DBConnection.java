package com.movieticket.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBConnection {
    private DBConnection() { }

    public static Connection getConnection() {
        Properties properties = new Properties();
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new IllegalStateException("db.properties was not found on the application classpath");
            }
            properties.load(input);
        } catch (Exception e) {
            if (e instanceof IllegalStateException) throw (IllegalStateException)e;
            throw new IllegalStateException("Unable to load database configuration", e);
        }

        String url = properties.getProperty("DB_URL");
        String user = properties.getProperty("DB_USER");
        String password = properties.getProperty("DB_PASSWORD");
        if (url == null || url.isBlank() || user == null || password == null) {
            throw new IllegalStateException("DB_URL, DB_USER and DB_PASSWORD must be configured in db.properties");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("MySQL JDBC driver is missing from WEB-INF/lib", e);
        }

        try {
            return DriverManager.getConnection(url.trim(), user.trim(), password);
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to connect to MySQL. Check db.properties and make sure MySQL is running.", e);
        }
    }
}