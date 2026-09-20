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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UserDAO userDAO;

	@Override
	public void init() {
		userDAO = new UserDAO();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (email == null || email.trim().isEmpty()) {

			request.setAttribute("emailError", "Email address is required.");

			request.getRequestDispatcher("/login.jsp").forward(request, response);

			return;
		}

		email = email.trim();

		String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

		if (!email.matches(emailRegex)) {

			request.setAttribute("emailError", "Please enter a valid email address.");

			request.getRequestDispatcher("/login.jsp").forward(request, response);

			return;
		}

		UserBean user = userDAO.loginUser(email, password);

		if (user != null) {

			HttpSession session = request.getSession();

			session.setAttribute("user", user);
			session.setAttribute("userId", user.getId());
			session.setAttribute("userName", user.getName());
			session.setAttribute("userRole", user.getRole());

			if (user.isStatus()) {

				session.removeAttribute("accountInactive");

				response.sendRedirect(request.getContextPath() + "/home");

			} else {

				session.setAttribute("accountInactive", true);

				response.sendRedirect(request.getContextPath() + "/profile");
			}

		} else {

			request.setAttribute("emailError", "Invalid email or password.");

			request.setAttribute("showForgotPassword", true);

			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

}
