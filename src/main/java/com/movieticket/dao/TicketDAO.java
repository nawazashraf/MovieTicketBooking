package com.movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.movieticket.model.TicketBean;
import com.movieticket.util.DBConnection;

public class TicketDAO {

	public TicketBean getTicketByBookingId(String bookingId) {

		String sql = """
				SELECT
				    b.id AS booking_id,
				    b.booking_reference,
				    b.total_amount,
				    b.booking_status,
				    b.created_at AS booking_date,

				    u.name AS customer_name,
				    u.email AS customer_email,
				    u.phone AS customer_phone,

				    m.title AS movie_title,
				    m.language,
				    m.duration_minutes,
				    m.certificate,
				    m.poster_url,

				    ma.name AS mall_name,
				    ma.address AS mall_address,
				    ma.city,
				    ma.state,
				    ma.pincode,

				    s.show_date,
				    s.start_time,
				    s.end_time,

				    p.payment_method,
				    p.transaction_id,
				    p.payment_status,
				    p.paid_at,

				    se.row_name,
				    se.seat_number,
				    st.type_name AS seat_type,
				    bs.price AS seat_price

				FROM bookings b

				JOIN users u
				    ON b.user_id = u.id

				JOIN shows s
				    ON b.show_id = s.id

				JOIN movies m
				    ON s.movie_id = m.id

				JOIN malls ma
				    ON s.mall_id = ma.id

				JOIN payments p
				    ON p.booking_id = b.id

				JOIN booking_seats bs
				    ON b.id = bs.booking_id

				JOIN show_seats ss
				    ON ss.id = bs.show_seat_id

				JOIN seats se
				    ON se.id = ss.seat_id

				JOIN seat_types st
				    ON se.seat_type_id = st.id

				WHERE b.id = ?
				""";

		try {

			Connection conn = DBConnection.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, bookingId);

			ResultSet rs = ps.executeQuery();

			TicketBean ticket = null;

			StringBuilder seats = new StringBuilder();

			while (rs.next()) {

				if (ticket == null) {

					ticket = new TicketBean();

					// Existing booking details
					ticket.setBookingId(
							rs.getString("booking_id"));

					ticket.setBookingReference(
							rs.getString("booking_reference"));

					ticket.setTotalAmount(
							rs.getBigDecimal("total_amount"));

					// Existing movie details
					ticket.setMovieTitle(
							rs.getString("movie_title"));

					// Existing mall details
					ticket.setMallName(
							rs.getString("mall_name"));

					// Existing show details
					ticket.setShowDate(
							rs.getDate("show_date"));

					ticket.setStartTime(
							rs.getTime("start_time"));

					// Existing payment details
					ticket.setPaymentMethod(
							rs.getString("payment_method"));

					ticket.setTransactionId(
							rs.getString("transaction_id"));

					ticket.setPaymentStatus(
							rs.getString("payment_status"));

					// Additional movie details
					ticket.setLanguage(
							rs.getString("language"));

					ticket.setDurationMinutes(
							rs.getInt("duration_minutes"));

					ticket.setCertificate(
							rs.getString("certificate"));

					ticket.setPosterUrl(
							rs.getString("poster_url"));

					// Additional cinema details
					ticket.setMallAddress(
							rs.getString("mall_address"));

					ticket.setCity(
							rs.getString("city"));

					ticket.setState(
							rs.getString("state"));

					ticket.setPincode(
							rs.getInt("pincode"));

					// Additional show details
					ticket.setEndTime(
							rs.getTime("end_time"));

					// Customer details
					ticket.setCustomerName(
							rs.getString("customer_name"));

					ticket.setCustomerEmail(
							rs.getString("customer_email"));

					ticket.setCustomerPhone(
							rs.getString("customer_phone"));

					// Booking details
					ticket.setBookingStatus(
							rs.getString("booking_status"));

					ticket.setBookingDate(
							rs.getTimestamp("booking_date"));

					// Payment details
					ticket.setPaidAt(
							rs.getTimestamp("paid_at"));
				}

				// Seat information
				if (seats.length() > 0) {

					seats.append(", ");

				}

				seats.append(rs.getString("row_name"));

				seats.append(rs.getInt("seat_number"));

			}

			if (ticket != null) {

				ticket.setSeats(seats.toString());

				return ticket;

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

}