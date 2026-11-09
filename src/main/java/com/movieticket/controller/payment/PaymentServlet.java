package com.movieticket.controller.payment;

import java.io.IOException;
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
import com.movieticket.model.PaymentBean;

/** Handles the payment page and sends the selected method to processing. */
@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		String bookingId = request.getParameter("bookingId");
		BookingBean booking = new BookingDAO().getBookingById(bookingId);
		if (booking == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Booking not found");
			return;
		}

		PaymentDAO paymentDAO = new PaymentDAO();
		PaymentBean payment = paymentDAO.getPaymentByBookingId(bookingId);
		if (payment == null) {
			payment = new PaymentBean();
			payment.setId(UUID.randomUUID().toString());
			payment.setBookingId(booking.getId());
			payment.setAmount(booking.getTotalAmount());
			payment.setPaymentStatus("PENDING");

			if (!paymentDAO.createPayment(payment)) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to create payment");
				return;
			}
		}

		request.setAttribute("booking", booking);
		request.getRequestDispatcher("/payment/payment.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		String bookingId = request.getParameter("bookingId");
		String paymentMethod = request.getParameter("paymentMethod");
		String upiId = request.getParameter("upiId");
		if (bookingId == null || bookingId.isBlank()
				|| !("CARD".equals(paymentMethod) || "UPI".equals(paymentMethod))) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid payment request");
			return;
		}

		if ("UPI".equals(paymentMethod)
				&& (upiId == null || !upiId.trim().matches("[A-Za-z0-9._-]{2,}@[A-Za-z][A-Za-z0-9.-]{1,62}"))) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "A valid UPI ID is required");
			return;
		}

		request.setAttribute("bookingId", bookingId);
		request.setAttribute("paymentMethod", paymentMethod);
		request.getRequestDispatcher("/payment/payment-processing.jsp").forward(request, response);
	}
}
