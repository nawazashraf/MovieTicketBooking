package com.movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.movieticket.model.BookingBean;
import com.movieticket.model.BookingSeatBean;
import com.movieticket.util.DBConnection;

public class BookingDAO {

	// GET BOOKING
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
				    s.start_time,
				    s.end_time
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

				booking.setEndTime(rs.getTime("end_time"));

				booking.setCreatedAt(rs.getTimestamp("created_at"));

				booking.setExpiresAt(rs.getTimestamp("expires_at"));

				return booking;
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	// CREATE BOOKING
	public boolean createBooking(BookingBean booking) {

		String sql = """
				INSERT INTO bookings
				(
				    id,
				    booking_reference,
				    user_id,
				    show_id,
				    total_amount,
				    booking_status,
				    expires_at
				)
				VALUES (?, ?, ?, ?, ?, ?, ?)
				""";

		try {

			Connection conn = DBConnection.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, booking.getId());

			ps.setString(2, booking.getBookingReference());

			ps.setString(3, booking.getUserId());

			ps.setString(4, booking.getShowId());

			ps.setBigDecimal(5, booking.getTotalAmount());

			ps.setString(6, booking.getBookingStatus());

			ps.setTimestamp(7, booking.getExpiresAt());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	// ADD BOOKING SEAT
	public boolean addBookingSeat(BookingSeatBean seat) {

		String sql = """
				INSERT INTO booking_seats
				(
				    id,
				    booking_id,
				    show_seat_id,
				    price
				)
				VALUES (?, ?, ?, ?)
				""";

		try {

			Connection conn = DBConnection.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, seat.getId());

			ps.setString(2, seat.getBookingId());

			ps.setString(3, seat.getShowSeatId());

			ps.setBigDecimal(4, seat.getPrice());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	// CONFIRM BOOKING
	
	
	public boolean confirmBookingAndSeats(String bookingId) {

	    String bookingSql = """
	            UPDATE bookings
	            SET booking_status = 'CONFIRMED'
	            WHERE id = ?
	            """;

	    String seatSql = """
	            UPDATE show_seats ss
	            JOIN booking_seats bs
	                ON ss.id = bs.show_seat_id
	            SET ss.status = 'BOOKED'
	            WHERE bs.booking_id = ?
	            """;

	    try {

	        Connection conn = DBConnection.getConnection();

	        // Update booking
	        PreparedStatement ps1 =
	                conn.prepareStatement(bookingSql);

	        ps1.setString(1, bookingId);

	        int bookingResult =
	                ps1.executeUpdate();

	        if (bookingResult == 0) {
	            return false;
	        }

	        // Update seats
	        PreparedStatement ps2 =
	                conn.prepareStatement(seatSql);

	        ps2.setString(1, bookingId);

	        ps2.executeUpdate();

	        return true;

	    } catch (Exception e) {

	        e.printStackTrace();
	    }

	    return false;
	}
}