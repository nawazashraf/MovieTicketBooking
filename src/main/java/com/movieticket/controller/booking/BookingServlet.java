package com.movieticket.controller.booking;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.movieticket.dao.BookingDAO;
import com.movieticket.model.BookingBean;
import com.movieticket.model.BookingSeatBean;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			// =========================================
			// HARDCODED VALUES FOR PRACTICAL
			// =========================================

			String userId = "user-001";

			String showId = "show-001";

			String[] showSeatIds = { "show-seat-001", "show-seat-002" };

			BigDecimal[] prices = { new BigDecimal("150.00"), new BigDecimal("150.00") };

			// =========================================
			// TOTAL AMOUNT
			// =========================================

			BigDecimal totalAmount = prices[0].add(prices[1]);

			// =========================================
			// CREATE BOOKING
			// =========================================

			BookingBean booking = new BookingBean();

			String bookingId = UUID.randomUUID().toString();

			booking.setId(bookingId);

			booking.setBookingReference("BOOK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

			booking.setUserId(userId);

			booking.setShowId(showId);

			booking.setTotalAmount(totalAmount);

			booking.setBookingStatus("PENDING");

			// =========================================
			// DAO
			// =========================================

			BookingDAO dao = new BookingDAO();

			// =========================================
			// INSERT BOOKING
			// =========================================

			boolean bookingSaved = dao.createBooking(booking);

			if (!bookingSaved) {

				response.getWriter().println("Booking insert failed");

				return;
			}

			// =========================================
			// INSERT SEATS
			// =========================================

			for (int i = 0; i < showSeatIds.length; i++) {

				BookingSeatBean seat = new BookingSeatBean();

				seat.setId(UUID.randomUUID().toString());

				seat.setBookingId(bookingId);

				seat.setShowSeatId(showSeatIds[i]);

				seat.setPrice(prices[i]);

				boolean seatSaved = dao.addBookingSeat(seat);

				if (!seatSaved) {

					response.getWriter().println("Seat insert failed");

					return;
				}
			}

			// =========================================
			// SUCCESS
			// =========================================

			response.getWriter()
					.println("<h2>Booking Successful</h2>" + "<p>Booking ID: " + bookingId + "</p>"
							+ "<p>Booking Reference: " + booking.getBookingReference() + "</p>" + "<p>Total Amount: ₹"
							+ totalAmount + "</p>");

		} catch (Exception e) {

			e.printStackTrace();

			response.getWriter().println("Error: " + e.getMessage());
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}
}