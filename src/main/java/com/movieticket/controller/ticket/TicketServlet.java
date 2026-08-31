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

@WebServlet("/ticket")
public class TicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(false);

		if (session == null ||
		    session.getAttribute("user") == null) {

		    response.sendRedirect(
		        request.getContextPath() + "/login.jsp"
		    );

		    return;
		}
		
		String bookingId = request.getParameter("bookingId");

		TicketDAO ticketDAO = new TicketDAO();

		TicketBean ticket = ticketDAO.getTicketByBookingId(bookingId);

		if (ticket == null) {

			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ticket not found");

			return;
		}

		request.setAttribute("ticket", ticket);

		request.getRequestDispatcher("/ticket/ticket.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
