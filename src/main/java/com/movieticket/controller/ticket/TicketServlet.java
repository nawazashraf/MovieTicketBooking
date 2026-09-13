package com.movieticket.controller.ticket;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.movieticket.dao.TicketDAO;
import com.movieticket.model.TicketBean;
import com.movieticket.util.EmailService;
import com.movieticket.util.PdfService;

@WebServlet("/ticket")
public class TicketServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		String bookingId = request.getParameter("bookingId");

		if (bookingId == null || bookingId.trim().isEmpty()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Booking ID is required");
			return;
		}

		TicketDAO ticketDAO = new TicketDAO();

		TicketBean ticket = ticketDAO.getTicketByBookingId(bookingId);

		if (ticket == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ticket not found");
			return;
		}

		if ("pdf".equals(request.getParameter("format"))) {

			byte[] pdfBytes = PdfService.generateTicketPdf(ticket);

			if (pdfBytes == null) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "PDF generation failed");
				return;
			}

			response.setContentType("application/pdf");

			response.setHeader("Content-Disposition",
					"inline; filename=Movie-Ticket-" + ticket.getBookingReference() + ".pdf");

			response.setContentLength(pdfBytes.length);

			response.getOutputStream().write(pdfBytes);

			return;
		}

		request.setAttribute("ticket", ticket);

		String emailSentKey = "ticketEmailSent_" + bookingId;

		if (session.getAttribute(emailSentKey) == null) {

			byte[] pdfBytes = PdfService.generateTicketPdf(ticket);

			if (pdfBytes != null) {

				EmailService.sendTicketEmail(ticket, session, pdfBytes);

				session.setAttribute(emailSentKey, true);
			}
		}

		request.getRequestDispatcher("/ticket/ticket.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}