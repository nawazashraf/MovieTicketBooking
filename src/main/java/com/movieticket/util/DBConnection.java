package com.movieticket.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    public static Connection getConnection() {

        try {

            Properties properties = new Properties();

            InputStream input = DBConnection.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");

            properties.load(input);

            String URL = properties.getProperty("DB_URL");

            String USER = properties.getProperty("DB_USER");

            String PASSWORD = properties.getProperty("DB_PASSWORD");

            String DRIVER = "com.mysql.cj.jdbc.Driver";

            Class.forName(DRIVER);

            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }
}