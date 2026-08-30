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
				    m.title AS movie_title,
				    ma.name AS mall_name,
				    s.show_date,
				    s.start_time,
				    p.payment_method,
				    p.transaction_id,
				    p.payment_status,
				    se.row_name,
				    se.seat_number

				FROM bookings b

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

					ticket.setBookingId(rs.getString("booking_id"));

					ticket.setBookingReference(rs.getString("booking_reference"));

					ticket.setTotalAmount(rs.getBigDecimal("total_amount"));

					ticket.setMovieTitle(rs.getString("movie_title"));

					ticket.setMallName(rs.getString("mall_name"));

					ticket.setShowDate(rs.getDate("show_date"));

					ticket.setStartTime(rs.getTime("start_time"));

					ticket.setPaymentMethod(rs.getString("payment_method"));

					ticket.setTransactionId(rs.getString("transaction_id"));

					ticket.setPaymentStatus(rs.getString("payment_status"));
				}

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