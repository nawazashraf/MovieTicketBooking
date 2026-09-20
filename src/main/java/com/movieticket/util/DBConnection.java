package com.movieticket.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class DBConnection {

	private static HikariDataSource dataSource;

	private DBConnection() {
	}

	private static synchronized HikariDataSource getDataSource() {

		if (dataSource != null) {
			return dataSource;
		}

		String url = System.getenv("MYSQL_URL");
		String user = System.getenv("MYSQL_USER");
		String password = System.getenv("MYSQL_PASSWORD");

		/*
		 * Render: Uses MYSQL_URL, MYSQL_USER and MYSQL_PASSWORD.
		 *
		 * Local Eclipse: Falls back to db.properties.
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

		HikariConfig config = new HikariConfig();

		config.setJdbcUrl(url.trim());
		config.setUsername(user.trim());
		config.setPassword(password);

		/*
		 * Connection pool settings
		 */
		config.setMaximumPoolSize(5);
		config.setMinimumIdle(2);

		/*
		 * Wait at most 10 seconds for a connection from the pool.
		 */
		config.setConnectionTimeout(10000);

		/*
		 * Recycle connections periodically.
		 */
		config.setMaxLifetime(600000);

		/*
		 * Validate connections before using them.
		 */
		config.setConnectionTestQuery("SELECT 1");

		dataSource = new HikariDataSource(config);

		return dataSource;
	}

	public static Connection getConnection() {

		try {

			long start = System.currentTimeMillis();

			Connection connection = getDataSource().getConnection();

			long end = System.currentTimeMillis();

			System.out.println("DB CONNECTION TIME: " + (end - start) + " ms");

			return connection;

		} catch (SQLException e) {

			throw new IllegalStateException("Unable to obtain database connection", e);
		}
	}
}