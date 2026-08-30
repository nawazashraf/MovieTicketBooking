package com.movieticket.controller.analytics;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.movieticket.dao.AnalyticsDAO;
import com.movieticket.model.AnalyticsBean;

@WebServlet("/analytics")
public class AnalyticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		AnalyticsDAO analyticsDAO = new AnalyticsDAO();

		AnalyticsBean analytics = analyticsDAO.getDashboardAnalytics();

		request.setAttribute("analytics", analytics);

		request.getRequestDispatcher("/analytics/analytics.jsp").forward(request, response);
	}

}
