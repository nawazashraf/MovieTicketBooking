package com.movieticket.controller.booking;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.movieticket.model.ShowBean;
import com.movieticket.dao.SeatSelectionDAO;

/** Loads show details and available seat data for the selection page. */
@WebServlet("/booking/seats")
public class SeatSelectionServlet extends HttpServlet {

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
		if (showId == null || showId.isBlank()) {
			response.getWriter().println("Show ID is missing");
			return;
		}

		try {
			SeatSelectionDAO dao = new SeatSelectionDAO();
			ShowBean show = dao.getShowDetails(showId);
			if (show == null) {
				response.getWriter().println("Show not found");
				return;
			}

			request.setAttribute("show", show);
			request.setAttribute("seats", dao.getSeatsByShowId(showId));
			request.setAttribute("showId", showId);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/booking/seat_selection.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException | IllegalStateException e) {
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