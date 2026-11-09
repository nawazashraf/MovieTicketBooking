package com.movieticket.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import com.movieticket.model.TicketBean;
import com.movieticket.util.DBConnection;

public class TicketDAO {

	// Get the ticket details and group its seats by type.
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
				    (
				        SELECT GROUP_CONCAT(DISTINCT g2.name SEPARATOR ', ')
				        FROM movie_genres mg2
				        JOIN genres g2
				            ON mg2.genre_id = g2.id
				        WHERE mg2.movie_id = m.id
				    ) AS genre,
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

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, bookingId);

			try (ResultSet rs = ps.executeQuery()) {

				TicketBean ticket = null;

				Map<String, StringBuilder> seatsByType = new LinkedHashMap<>();
				Map<String, BigDecimal> priceByType = new LinkedHashMap<>();

				while (rs.next()) {

					if (ticket == null) {

						ticket = new TicketBean();

						ticket.setBookingId(rs.getString("booking_id"));

						ticket.setBookingReference(rs.getString("booking_reference"));

						ticket.setTotalAmount(rs.getBigDecimal("total_amount"));

						ticket.setBookingStatus(rs.getString("booking_status"));

						ticket.setBookingDate(rs.getTimestamp("booking_date"));

						ticket.setCustomerName(rs.getString("customer_name"));

						ticket.setCustomerEmail(rs.getString("customer_email"));

						ticket.setCustomerPhone(rs.getString("customer_phone"));

						ticket.setMovieTitle(rs.getString("movie_title"));

						ticket.setLanguage(rs.getString("language"));

						ticket.setDurationMinutes(rs.getInt("duration_minutes"));

						ticket.setCertificate(rs.getString("certificate"));

						ticket.setGenre(rs.getString("genre"));

						ticket.setPosterUrl(rs.getString("poster_url"));

						ticket.setMallName(rs.getString("mall_name"));

						ticket.setMallAddress(rs.getString("mall_address"));

						ticket.setCity(rs.getString("city"));

						ticket.setState(rs.getString("state"));

						ticket.setPincode(rs.getInt("pincode"));

						ticket.setShowDate(rs.getDate("show_date"));

						ticket.setStartTime(rs.getTime("start_time"));

						ticket.setEndTime(rs.getTime("end_time"));

						ticket.setPaymentMethod(rs.getString("payment_method"));

						ticket.setTransactionId(rs.getString("transaction_id"));

						ticket.setPaymentStatus(rs.getString("payment_status"));

						ticket.setPaidAt(rs.getTimestamp("paid_at"));
					}

					String seatType = rs.getString("seat_type");

					String seat = rs.getString("row_name") + rs.getInt("seat_number");

					BigDecimal seatPrice = rs.getBigDecimal("seat_price");
					// The query has one row per seat, so gather them by type here.
					StringBuilder typeSeats = seatsByType.computeIfAbsent(seatType, key -> new StringBuilder());

					if (typeSeats.length() > 0) {
						typeSeats.append(", ");
					}

					typeSeats.append(seat);

					priceByType.merge(seatType, seatPrice, BigDecimal::add);
				}

				if (ticket != null) {

					StringBuilder seats = new StringBuilder();
					StringBuilder seatTypes = new StringBuilder();
					StringBuilder seatPrices = new StringBuilder();

					for (String seatType : seatsByType.keySet()) {

						if (seats.length() > 0) {
							seats.append("|");
							seatTypes.append("|");
							seatPrices.append("|");
						}

						seats.append(seatsByType.get(seatType));

						seatTypes.append(seatType);

						seatPrices.append(priceByType.get(seatType));
					}

					ticket.setSeats(seats.toString());

					ticket.setSeatTypes(seatTypes.toString());

					ticket.setSeatPrices(seatPrices.toString());

					return ticket;
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}
}