package com.movieticket.controller.booking;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.movieticket.dao.BookingDAO;
import com.movieticket.dao.PaymentDAO;
import com.movieticket.model.BookingBean;
import com.movieticket.model.BookingSeatBean;
import com.movieticket.model.PaymentBean;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("user") == null) {

			response.sendRedirect(request.getContextPath() + "/login.jsp");

			return;
		}

		try {

			String userId = (String) session.getAttribute("userId");

			String showId = request.getParameter("showId");
			String selectedSeats = request.getParameter("selectedSeats");

			if (showId == null || showId.trim().isEmpty()) {

				response.getWriter().println("Show ID is missing");
				return;
			}

			if (selectedSeats == null || selectedSeats.trim().isEmpty()) {

				response.getWriter().println("No seats selected");
				return;
			}

			String[] showSeatIds = selectedSeats.split(",");

			System.out.println("Selected seats: " + selectedSeats);

			for (String showSeatId : showSeatIds) {
				System.out.println("Show Seat ID: [" + showSeatId.trim() + "]");
			}

			BookingDAO dao = new BookingDAO();

			BigDecimal totalAmount = BigDecimal.ZERO;

			for (String showSeatId : showSeatIds) {

				showSeatId = showSeatId.trim();

				BigDecimal price = dao.getSeatPrice(showSeatId);

				if (price == null) {
					response.getWriter().println("Invalid seat: " + showSeatId);
					return;
				}

				totalAmount = totalAmount.add(price);
			}
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

			for (String showSeatId : showSeatIds) {

				showSeatId = showSeatId.trim();

				BigDecimal price = dao.getSeatPrice(showSeatId);

				if (price == null) {
					response.getWriter().println("Invalid seat: " + showSeatId);
					return;
				}

				BookingSeatBean seat = new BookingSeatBean();

				seat.setId(UUID.randomUUID().toString());

				seat.setBookingId(bookingId);

				seat.setShowSeatId(showSeatId);

				seat.setPrice(price);

				boolean seatSaved = dao.addBookingSeat(seat);

				if (!seatSaved) {

					response.getWriter().println("Seat insert failed");

					return;
				}
			}

			PaymentBean payment = new PaymentBean();

			payment.setId(UUID.randomUUID().toString());
			payment.setBookingId(bookingId);
			payment.setAmount(totalAmount);
			payment.setPaymentStatus("PENDING");

			PaymentDAO paymentDAO = new PaymentDAO();

			boolean paymentCreated = paymentDAO.createPayment(payment);

			if (!paymentCreated) {

				response.getWriter().println("Payment creation failed");

				return;
			}

			// =========================================
			// GO TO PAYMENT
			// =========================================

			response.sendRedirect(request.getContextPath() + "/payment?bookingId=" + bookingId);

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