package com.movieticket.controller.booking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/booking-summary")
public class BookingSummaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String showId = request.getParameter("showId");
		String selectedSeats = request.getParameter("selectedSeats");

		System.out.println("Show ID: " + showId);
		System.out.println("Selected Seats: " + selectedSeats);

		response.getWriter().println("Show ID: " + showId + "<br>" + "Selected Seats: " + selectedSeats);
	}

}
