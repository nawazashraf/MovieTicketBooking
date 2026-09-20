package com.movieticket.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

		try (Connection conn = DBConnection.getConnection();

				PreparedStatement ps = conn.prepareStatement(sql)) {

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

		try (Connection conn = DBConnection.getConnection();

				PreparedStatement ps = conn.prepareStatement(sql);) {

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

		try (Connection conn = DBConnection.getConnection();

				PreparedStatement ps = conn.prepareStatement(sql)) {

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

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps1 = conn.prepareStatement(bookingSql);
				PreparedStatement ps2 = conn.prepareStatement(seatSql)) {

			conn.setAutoCommit(false);

			try {
				ps1.setString(1, bookingId);

				if (ps1.executeUpdate() == 0) {
					conn.rollback();
					return false;
				}

				ps2.setString(1, bookingId);
				ps2.executeUpdate();

				conn.commit();
				return true;

			} catch (Exception e) {
				conn.rollback();
				throw e;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public BigDecimal getSeatPrice(String showSeatId) {
		String sql = """
				SELECT
					price
				FROM
					show_seats
				WHERE
					id = ?
				""";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, showSeatId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getBigDecimal("price");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<BookingSeatBean> getSelectedSeatDetails(String[] showSeatIds) {

		List<BookingSeatBean> seats = new ArrayList<>();

		if (showSeatIds == null || showSeatIds.length == 0) {
			return seats;
		}

		StringBuilder placeholders = new StringBuilder();

		for (int i = 0; i < showSeatIds.length; i++) {
			if (i > 0) {
				placeholders.append(",");
			}
			placeholders.append("?");
		}

		String sql = """
				SELECT id, price, status
				FROM show_seats
				WHERE id IN (%s)
				""".formatted(placeholders);

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			for (int i = 0; i < showSeatIds.length; i++) {
				ps.setString(i + 1, showSeatIds[i].trim());
			}

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					BookingSeatBean seat = new BookingSeatBean();

					seat.setShowSeatId(rs.getString("id"));
					seat.setPrice(rs.getBigDecimal("price"));

					seats.add(seat);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return seats;
	}

	public boolean addBookingSeats(List<BookingSeatBean> seats) {

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

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			for (BookingSeatBean seat : seats) {

				ps.setString(1, seat.getId());
				ps.setString(2, seat.getBookingId());
				ps.setString(3, seat.getShowSeatId());
				ps.setBigDecimal(4, seat.getPrice());

				ps.addBatch();
			}

			ps.executeBatch();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<BookingBean> getBookingsByUserId(String userId) {
		String sql = "SELECT b.id, b.booking_reference, b.user_id, b.show_id, b.total_amount, b.booking_status, b.created_at, b.expires_at, "
				+ "m.title AS movie_title, m.poster_url, ma.name AS mall_name, s.show_date, s.start_time, s.end_time, "
				+ "GROUP_CONCAT(CONCAT(st.type_name, ' ', seats.row_name, seats.seat_number) ORDER BY seats.row_name, seats.seat_number SEPARATOR ', ') AS seat_labels "
				+ "FROM bookings b JOIN shows s ON s.id=b.show_id JOIN movies m ON m.id=s.movie_id JOIN malls ma ON ma.id=s.mall_id "
				+ "LEFT JOIN booking_seats bs ON bs.booking_id=b.id LEFT JOIN show_seats ss ON ss.id=bs.show_seat_id "
				+ "LEFT JOIN seats ON seats.id=ss.seat_id LEFT JOIN seat_types st ON st.id=seats.seat_type_id "
				+ "WHERE b.user_id=? GROUP BY b.id ORDER BY b.created_at DESC";
		List<BookingBean> list = new ArrayList<>();
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					BookingBean b = new BookingBean();
					b.setId(rs.getString("id"));
					b.setBookingReference(rs.getString("booking_reference"));
					b.setUserId(rs.getString("user_id"));
					b.setShowId(rs.getString("show_id"));
					b.setTotalAmount(rs.getBigDecimal("total_amount"));
					b.setBookingStatus(rs.getString("booking_status"));
					b.setMovieTitle(rs.getString("movie_title"));
					b.setPosterUrl(rs.getString("poster_url"));
					b.setMallName(rs.getString("mall_name"));
					b.setShowDate(rs.getDate("show_date"));
					b.setStartTime(rs.getTime("start_time"));
					b.setEndTime(rs.getTime("end_time"));
					b.setCreatedAt(rs.getTimestamp("created_at"));
					b.setExpiresAt(rs.getTimestamp("expires_at"));
					b.setSeatLabels(rs.getString("seat_labels"));
					list.add(b);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}