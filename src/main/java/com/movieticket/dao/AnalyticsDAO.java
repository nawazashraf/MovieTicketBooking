package com.movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.movieticket.model.AnalyticsBean;
import com.movieticket.model.MovieAnalyticsBean;
import com.movieticket.util.DBConnection;

public class AnalyticsDAO {

	public AnalyticsBean getDashboardAnalytics() {

		String sql = """
				SELECT

				    -- Total Revenue
				    (
				        SELECT COALESCE(SUM(amount), 0)
				        FROM payments
				        WHERE payment_status = 'SUCCESS'
				    ) AS total_revenue,

				    -- Total Bookings
				    (
				        SELECT COUNT(*)
				        FROM bookings
				    ) AS total_bookings,

				    -- Confirmed Bookings
				    (
				        SELECT COUNT(*)
				        FROM bookings
				        WHERE booking_status = 'CONFIRMED'
				    ) AS confirmed_bookings,

				    -- Total Tickets Sold
				    (
				        SELECT COUNT(*)
				        FROM booking_seats bs
				        JOIN bookings b
				            ON b.id = bs.booking_id
				        WHERE b.booking_status = 'CONFIRMED'
				    ) AS total_tickets_sold,

				    -- Total Users
				    (
				        SELECT COUNT(*)
				        FROM users
				        WHERE role = 'USER'
				    ) AS total_users,

				    -- Revenue Today
				    (
				        SELECT COALESCE(SUM(amount), 0)
				        FROM payments
				        WHERE payment_status = 'SUCCESS'
				        AND DATE(paid_at) = CURDATE()
				    ) AS revenue_today,

				    -- Revenue This Month
				    (
				        SELECT COALESCE(SUM(amount), 0)
				        FROM payments
				        WHERE payment_status = 'SUCCESS'
				        AND YEAR(paid_at) = YEAR(CURDATE())
				        AND MONTH(paid_at) = MONTH(CURDATE())
				    ) AS revenue_this_month
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

				analytics.setTotalUsers(rs.getInt("total_users"));

				analytics.setRevenueToday(rs.getBigDecimal("revenue_today"));

				analytics.setRevenueThisMonth(rs.getBigDecimal("revenue_this_month"));

				return analytics;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<MovieAnalyticsBean> getTopMovies() {

		List<MovieAnalyticsBean> movies = new ArrayList<>();

		String sql = """
				SELECT
					m.title,
					COUNT(bs.id) AS tickets_sold,
					COALESCE(SUM(bs.price), 0) AS revenue

				FROM movies m

				JOIN shows s
				        ON s.movie_id = m.id

				    JOIN bookings b
				        ON b.show_id = s.id

				    JOIN booking_seats bs
				        ON bs.booking_id = b.id

				    WHERE b.booking_status = 'CONFIRMED'

				    GROUP BY m.id, m.title

				    ORDER BY tickets_sold DESC

				    LIMIT 5
				""";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					MovieAnalyticsBean movie = new MovieAnalyticsBean();

					movie.setMovieTitle(rs.getString("title"));
					movie.setTicketsSold(rs.getInt("tickets_sold"));
					movie.setRevenue(rs.getDouble("revenue"));

					movies.add(movie);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movies;
	}
}