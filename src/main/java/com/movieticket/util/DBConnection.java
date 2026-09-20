package com.movieticket.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBConnection {

	private DBConnection() {
	}

	public static Connection getConnection() {

		String url = System.getenv("MYSQL_URL");
		String user = System.getenv("MYSQL_USER");
		String password = System.getenv("MYSQL_PASSWORD");

		/*
		 * If environment variables are not available, use db.properties for local
		 * development.
		 */
		if (url == null || url.isBlank() || user == null || user.isBlank() || password == null || password.isBlank()) {

			Properties properties = new Properties();

			try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {

				if (input == null) {
					throw new IllegalStateException("db.properties was not found on the application classpath");
				}

				properties.load(input);

			} catch (Exception e) {

				if (e instanceof IllegalStateException) {
					throw (IllegalStateException) e;
				}

				throw new IllegalStateException("Unable to load database configuration", e);
			}

			url = properties.getProperty("DB_URL");
			user = properties.getProperty("DB_USER");
			password = properties.getProperty("DB_PASSWORD");
		}

		if (url == null || url.isBlank() || user == null || user.isBlank() || password == null || password.isBlank()) {

			throw new IllegalStateException("Database configuration is missing");
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("MySQL JDBC driver is missing from WEB-INF/lib", e);
		}

		try {
			return DriverManager.getConnection(url.trim(), user.trim(), password.trim());

		} catch (SQLException e) {

			throw new IllegalStateException("Unable to connect to MySQL database.", e);
		}
	}
}