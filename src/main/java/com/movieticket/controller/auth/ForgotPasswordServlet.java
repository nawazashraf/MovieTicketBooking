package com.movieticket.controller.auth;

import com.movieticket.dao.UserDAO;
import com.movieticket.model.UserBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/forgotpassword")
public class ForgotPasswordServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UserDAO userDAO;

	@Override
	public void init() {

		userDAO = new UserDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/forgotpassword.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");

		if (email == null || email.trim().isEmpty()) {

			request.setAttribute("error", "Email address is required.");

			request.getRequestDispatcher("/forgotpassword.jsp").forward(request, response);

			return;
		}

		email = email.trim();

		// Find user using existing DAO method
		UserBean user = userDAO.getUserByEmail(email);

		if (user != null) {

			HttpSession session = request.getSession();

			session.setAttribute("forgotUserId", user.getId());

			session.setAttribute("forgotUserEmail", user.getEmail());

			request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

		} else {

			request.setAttribute("error", "No account is registered with this email address.");

			request.getRequestDispatcher("/forgotpassword.jsp").forward(request, response);
		}
	}
}