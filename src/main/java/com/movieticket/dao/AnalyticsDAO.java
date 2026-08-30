package com.movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.movieticket.model.AnalyticsBean;
import com.movieticket.util.DBConnection;

public class AnalyticsDAO {
	public AnalyticsBean getDashboardAnalytics() {
		String sql = """
					SELECT
				(
				 		SELECT
				   		COALESCE(SUM(amount),0)
				 		FROM
				   		payments p
				 		WHERE
				   		p.payment_status = 'SUCCESS'
				) AS total_revenue,
				(
				 		SELECT
				   		COUNT(*)
				 	FROM
				   	bookings
				) AS total_bookings,
				(
				 		SELECT
				   		COUNT(*) AS confirmed_bookings
				 		FROM
				   		bookings b
				 		WHERE
				   		b.booking_status = 'CONFIRMED'
				) AS confirmed_bookings,
				(
				 		SELECT
				   		COUNT(*)
				 		FROM
				   		booking_seats
				) AS total_tickets_sold
					""";

		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			AnalyticsBean analytics = new AnalyticsBean();

			if (rs.next()) {
				analytics.setTotalRevenue(rs.getBigDecimal("total_revenue"));
				analytics.setTotalBookings(rs.getInt("total_bookings"));
				analytics.setConfirmedBookings(rs.getInt("confirmed_bookings"));
				analytics.setTotalTicketsSold(rs.getInt("total_tickets_sold"));

				// Will add more analytics if my teamate starts working
				
				return analytics;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
