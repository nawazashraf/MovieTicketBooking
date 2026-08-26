package com.movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.movieticket.model.BookingBean;
import com.movieticket.util.DBConnection;

public class BookingDAO {
	public BookingBean getBookingById(String id) {
		String sql = """
					SELECT *
					FROM bookings
					WHERE id = ?
				""";

		try {
			Connection conn = DBConnection.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				BookingBean booking = new BookingBean();

				booking.setId(rs.getString("id"));
				booking.setBookingReference(rs.getString("booking_reference"));
				booking.setUserId(rs.getString("user_id"));
				booking.setShowId(rs.getString("show_id"));
				booking.setTotalAmount(rs.getBigDecimal("total_amount"));
				booking.setBookingStatus(rs.getString("booking_status"));
				booking.setCreatedAt(rs.getTimestamp("created_at"));
				booking.setExpiresAt(rs.getTimestamp("expires_at"));

				return booking;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
