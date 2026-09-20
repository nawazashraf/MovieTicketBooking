package com.movieticket.controller.booking;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

			// =========================================
			// FETCH ALL SEAT PRICES - ONE QUERY
			// =========================================

			BookingDAO bookingDAO = new BookingDAO();

			List<BookingSeatBean> seats = bookingDAO.getSelectedSeatDetails(showSeatIds);

			if (seats.size() != showSeatIds.length) {
				response.getWriter().println("One or more seats are invalid.");
				return;
			}

			// =========================================
			// CALCULATE TOTAL
			// =========================================

			BigDecimal totalAmount = BigDecimal.ZERO;

			for (BookingSeatBean seat : seats) {
				totalAmount = totalAmount.add(seat.getPrice());
			}

			// =========================================
			// CREATE BOOKING
			// =========================================

			String bookingId = UUID.randomUUID().toString();

			BookingBean booking = new BookingBean();

			booking.setId(bookingId);

			booking.setBookingReference("BOOK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

			booking.setUserId(userId);
			booking.setShowId(showId);
			booking.setTotalAmount(totalAmount);
			booking.setBookingStatus("PENDING");

			boolean bookingSaved = bookingDAO.createBooking(booking);

			if (!bookingSaved) {
				response.getWriter().println("Booking insert failed");
				return;
			}

			// =========================================
			// PREPARE BOOKING SEATS
			// =========================================

			for (BookingSeatBean seat : seats) {

				seat.setId(UUID.randomUUID().toString());
				seat.setBookingId(bookingId);
			}

			// =========================================
			// INSERT ALL SEATS - ONE BATCH
			// =========================================

			boolean seatsSaved = bookingDAO.addBookingSeats(seats);

			if (!seatsSaved) {
				response.getWriter().println("Seat insertion failed");
				return;
			}

			// =========================================
			// CREATE PAYMENT
			// =========================================

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