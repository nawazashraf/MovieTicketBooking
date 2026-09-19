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

@WebServlet("/update-profile")
public class UpdateProfileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UserDAO userDAO;

	@Override
	public void init() {
		userDAO = new UserDAO();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("userId") == null) {

			response.sendRedirect(request.getContextPath() + "/login.jsp");

			return;
		}

		String userId = String.valueOf(session.getAttribute("userId"));

		UserBean user = userDAO.getUserById(userId);

		if (user == null || !user.isStatus()) {

			response.sendRedirect(request.getContextPath() + "/profile");

			return;
		}

		String name = request.getParameter("name");

		String phone = request.getParameter("phone");

		if (name == null || name.trim().isEmpty() || phone == null || phone.trim().isEmpty()) {

			request.setAttribute("updateError", "Name and phone number are required.");

			request.setAttribute("user", user);

			request.getRequestDispatcher("/profile.jsp").forward(request, response);

			return;
		}

		name = name.trim();
		phone = phone.trim();

		/*
		 * Convert name to First Letter Capital for every word.
		 *
		 * Example: ahsan kamal -> Ahsan Kamal AHSAN KAMAL -> Ahsan Kamal ahSAN kAMAL ->
		 * Ahsan Kamal
		 */
		String[] words = name.toLowerCase().split("\\s+");

		StringBuilder formattedName = new StringBuilder();

		for (String word : words) {

			if (!word.isEmpty()) {

				formattedName.append(Character.toUpperCase(word.charAt(0)));

				if (word.length() > 1) {

					formattedName.append(word.substring(1));
				}

				formattedName.append(" ");
			}
		}

		name = formattedName.toString().trim();

		if (!phone.matches("\\d{10}")) {

			request.setAttribute("updateError", "Phone number must contain exactly 10 digits.");

			request.setAttribute("user", user);

			request.getRequestDispatcher("/profile.jsp").forward(request, response);

			return;
		}

		boolean updated = userDAO.updateProfile(userId, name, phone);

		if (updated) {

			UserBean updatedUser = userDAO.getUserById(userId);

			session.setAttribute("user", updatedUser);

			session.setAttribute("userName", updatedUser.getName());

			session.setAttribute("userRole", updatedUser.getRole());

			request.setAttribute("updateSuccess", "Profile updated successfully.");

			request.setAttribute("user", updatedUser);

		} else {

			request.setAttribute("updateError", "Profile update failed. Please try again.");

			request.setAttribute("user", user);
		}

		request.getRequestDispatcher("/profile.jsp").forward(request, response);
	}
}