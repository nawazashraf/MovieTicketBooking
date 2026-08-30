package com.movieticket.controller.booking;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.movieticket.model.ShowBean;
import com.movieticket.model.SeatBean;
import com.movieticket.dao.SeatSelection;

@WebServlet("/SeatSelectionServlet")
public class SeatSelectionServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//String showId = request.getParameter("showId");
		
		String showId = "show-001";
		

		// Check showId
		if (showId == null || showId.trim().isEmpty()) {

			response.getWriter().println("Show ID is missing");

			return;
		}

		try {

			SeatSelection dao = new SeatSelection();

			// =============================================
			// GET SHOW INFORMATION
			// =============================================

			ShowBean show = dao.getShowDetails(showId);

			if (show == null) {

				response.getWriter().println("Show not found");

				return;
			}

			// =============================================
			// GET SEATS
			// =============================================

			ArrayList<SeatBean> seats = dao.getSeatsByShowId(showId);

			// =============================================
			// SEND DATA TO JSP
			// =============================================

			request.setAttribute("show", show);

			request.setAttribute("seats", seats);

			request.setAttribute("showId", showId);

			// =============================================
			// OPEN SEAT SELECTION JSP
			// =============================================

			RequestDispatcher rd = request.getRequestDispatcher("seat_selection.jsp");

			rd.forward(request, response);

		} catch (Exception e) {

			e.printStackTrace();

			response.getWriter().println("Database Error: " + e.getMessage());
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}
}