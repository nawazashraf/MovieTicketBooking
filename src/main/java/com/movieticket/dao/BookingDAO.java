package com.movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.movieticket.model.BookingBean;
import com.movieticket.util.DBConnection;

public class BookingDAO {
	public BookingBean getBookingById(String id) {
		String sql = """
				SELECT
				    b.id,
				    b.booking_reference,
				    b.user_id,
				    b.show_id,
				    b.total_amount,
				    b.booking_status,
				    b.created_at,
				    b.expires_at,

				    m.title AS movie_title,
				    m.poster_url AS poster_url,

				    ma.name AS mall_name,

				    s.show_date,
				    s.start_time

				FROM bookings b

				JOIN shows s
				    ON s.id = b.show_id

				JOIN movies m
				    ON m.id = s.movie_id

				JOIN malls ma
				    ON ma.id = s.mall_id

				WHERE b.id = ?
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

				booking.setMovieTitle(rs.getString("movie_title"));
				booking.setPosterUrl(rs.getString("poster_url"));
				booking.setMallName(rs.getString("mall_name"));
				booking.setShowDate(rs.getDate("show_date"));
				booking.setStartTime(rs.getTime("start_time"));

				booking.setCreatedAt(rs.getTimestamp("created_at"));
				booking.setExpiresAt(rs.getTimestamp("expires_at"));

				return booking;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean confirmBooking(String bookingId) {
		String sql = """
				UPDATE bookings
				SET booking_status = 'CONFIRMED'
				WHERE id = ?
				""";

		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, bookingId);

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

}
