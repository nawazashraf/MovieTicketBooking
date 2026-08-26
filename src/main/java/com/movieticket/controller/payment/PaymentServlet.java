package com.movieticket.controller.payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import com.movieticket.dao.BookingDAO;
import com.movieticket.dao.PaymentDAO;
import com.movieticket.model.BookingBean;
import com.movieticket.model.PaymentBean;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String bookingId = request.getParameter("bookingId");

		BookingDAO bookingDAO = new BookingDAO();

		BookingBean booking = bookingDAO.getBookingById(bookingId);

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

			boolean paymentCreated = paymentDAO.createPayment(payment);

			if (!paymentCreated) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to create payment");
				return;
			}
		}

		request.setAttribute("booking", booking);

		request.getRequestDispatcher("/payment/payment.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bookingId = request.getParameter("bookingId");
		String paymentMethod = request.getParameter("paymentMethod");

		String transactionId = "TXN-" + UUID.randomUUID().toString();

		PaymentDAO paymentDAO = new PaymentDAO();

		boolean paymentSuccess = paymentDAO.markPaymentSuccess(bookingId, paymentMethod, transactionId);

		if (paymentSuccess) {

			BookingDAO bookingDAO = new BookingDAO();

			boolean bookingConfirmed = bookingDAO.confirmBooking(bookingId);

			if (bookingConfirmed) {
				response.sendRedirect(request.getContextPath() + "/payment?bookingId=" + bookingId);

			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Payment Failed");
			}
		}

	}

}
