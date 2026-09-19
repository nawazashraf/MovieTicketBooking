package com.movieticket.controller.payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.UUID;

import com.movieticket.dao.BookingDAO;
import com.movieticket.dao.PaymentDAO;
import com.movieticket.dao.TicketDAO;
import com.movieticket.model.BookingBean;
import com.movieticket.model.PaymentBean;
import com.movieticket.model.TicketBean;
import com.movieticket.util.EmailService;
import com.movieticket.util.PdfService;

@WebServlet("/payment/process")
public class PaymentProcessingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		String bookingId = request.getParameter("bookingId");
		String paymentMethod = request.getParameter("paymentMethod");

		if (bookingId == null || paymentMethod == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid payment request");
			return;
		}

		PaymentDAO paymentDAO = new PaymentDAO();

		String transactionId = "TXN-" + UUID.randomUUID();

		boolean paymentSuccess = paymentDAO.markPaymentSuccess(bookingId, paymentMethod, transactionId);

		if (!paymentSuccess) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Payment failed");
			return;
		}

		BookingDAO bookingDAO = new BookingDAO();

		boolean bookingConfirmed = bookingDAO.confirmBookingAndSeats(bookingId);

		if (!bookingConfirmed) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Payment successful but booking confirmation failed");
			return;
		}

		/*
		 * GET TICKET
		 */
		TicketDAO ticketDAO = new TicketDAO();

		TicketBean ticket = ticketDAO.getTicketByBookingId(bookingId);

		/*
		 * SEND EMAIL ONCE AFTER SUCCESSFUL BOOKING
		 */
		if (ticket != null) {

			byte[] pdfBytes = PdfService.generateTicketPdf(ticket);

			if (pdfBytes != null) {

				EmailService.sendTicketEmail(ticket, session, pdfBytes);
			}
		}

		/*
		 * SHOW TICKET
		 */
		response.sendRedirect(request.getContextPath() + "/ticket?bookingId=" + bookingId);
	}
}