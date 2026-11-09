package com.movieticket.controller.booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/** Displays the selected show and seats in the booking summary response. */
@WebServlet("/booking-summary")
public class BookingSummaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		String showId = request.getParameter("showId");
		String selectedSeats = request.getParameter("selectedSeats");

		response.getWriter().println("Show ID: " + showId + "<br>" + "Selected Seats: " + selectedSeats);
	}

}
