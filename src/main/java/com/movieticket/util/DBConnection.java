package com.movieticket.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public static Connection getConnection() {
		try {
			String URL = "jdbc:mysql://localhost:3306/movie_ticket_booking";

			String USER = "root";

			String PASSWORD = "your_mysql_password";

			String DRIVER = "com.mysql.cj.jdbc.Driver";

			Class.forName(DRIVER);

			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
