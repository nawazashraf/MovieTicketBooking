
package com.movieticket.controller.auth;

import com.movieticket.dao.UserDAO;
import com.movieticket.model.UserBean;
import com.movieticket.util.EmailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

		String password = request.getParameter("password");

		String confirmPassword = request.getParameter("confirmPassword");

		/*
		 * ======================================== REQUIRED FIELDS
		 * ========================================
		 */

		if (name == null || name.trim().isEmpty() || phone == null || phone.trim().isEmpty()) {

			request.setAttribute("updateError", "Name and phone number are required.");

			request.setAttribute("user", user);

			request.getRequestDispatcher("/profile.jsp").forward(request, response);

			return;
		}

		name = name.trim();
		phone = phone.trim();

		/*
		 * ======================================== NAME VALIDATION
		 * ========================================
		 *
		 * Extra spaces are normalized.
		 *
		 * Example: John Smith becomes John Smith
		 */

		name = name.replaceAll("\\s+", " ");

		if (name.length() < 2 || name.length() > 50) {

			request.setAttribute("updateError", "Name must be between 2 and 50 characters.");

			request.setAttribute("user", user);

			request.getRequestDispatcher("/profile.jsp").forward(request, response);

			return;
		}

		if (!name.matches("[A-Za-z ]+")) {

			request.setAttribute("updateError", "Name can contain only letters and spaces.");

			request.setAttribute("user", user);

			request.getRequestDispatcher("/profile.jsp").forward(request, response);

			return;
		}

		/*
		 * ======================================== FORMAT NAME
		 * ========================================
		 *
		 * john doe -> John Doe JOHN DOE -> John Doe jOhN dOe -> John Doe
		 */

		String[] words = name.toLowerCase().split(" ");

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

		/*
		 * ======================================== PHONE VALIDATION
		 * ========================================
		 *
		 * Exactly 10 digits.
		 */

		if (!phone.matches("\\d{10}")) {

			request.setAttribute("updateError", "Phone number must contain exactly 10 digits.");

			request.setAttribute("user", user);

			request.getRequestDispatcher("/profile.jsp").forward(request, response);

			return;
		}

		/*
		 * ======================================== PHONE NUMBER SHOULD NOT START WITH 0
		 * ========================================
		 */

		if (phone.startsWith("0")) {

			request.setAttribute("updateError", "Please enter a valid 10-digit mobile number.");

			request.setAttribute("user", user);

			request.getRequestDispatcher("/profile.jsp").forward(request, response);

			return;
		}

		/*
		 * ======================================== PASSWORD
		 * ========================================
		 */

		boolean changePassword = password != null && !password.trim().isEmpty();

		if (changePassword) {

			/*
			 * No leading/trailing spaces.
			 */

			if (!password.equals(password.trim())) {

				request.setAttribute("updateError", "Password must not contain leading or trailing spaces.");

				request.setAttribute("user", user);

				request.getRequestDispatcher("/profile.jsp").forward(request, response);

				return;
			}

			/*
			 * Minimum 8 characters.
			 */

			if (password.length() < 8) {

				request.setAttribute("updateError", "Password must contain at least 8 characters.");

				request.setAttribute("user", user);

				request.getRequestDispatcher("/profile.jsp").forward(request, response);

				return;
			}

			/*
			 * Maximum 128 characters.
			 */

			if (password.length() > 128) {

				request.setAttribute("updateError", "Password cannot exceed 128 characters.");

				request.setAttribute("user", user);

				request.getRequestDispatcher("/profile.jsp").forward(request, response);

				return;
			}

			/*
			 * Confirm password.
			 */

			if (confirmPassword == null || !password.equals(confirmPassword)) {

				request.setAttribute("updateError", "Passwords do not match.");

				request.setAttribute("user", user);

				request.getRequestDispatcher("/profile.jsp").forward(request, response);

				return;
			}
		}

		/*
		 * ======================================== UPDATE PROFILE
		 * ========================================
		 */

		boolean updated;

		if (changePassword) {

			updated = userDAO.updateProfile(userId, name, phone, password);

		} else {

			updated = userDAO.updateProfile(userId, name, phone);
		}

		/*
		 * ======================================== RESULT
		 * ========================================
		 */

		if (updated) {

			UserBean updatedUser = userDAO.getUserById(userId);

			session.setAttribute("user", updatedUser);

			session.setAttribute("userName", updatedUser.getName());

			session.setAttribute("userRole", updatedUser.getRole());

			/*
			 * ======================================== PASSWORD CHANGE EMAIL
			 * ========================================
			 */

			if (changePassword) {

				try {

					String changedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));

					EmailService.sendPasswordChangedEmail(updatedUser.getEmail(), updatedUser.getName(), changedAt);

				} catch (Exception e) {

					e.printStackTrace();
				}
			}

			request.setAttribute("updateSuccess", "Profile updated successfully.");

			request.setAttribute("user", updatedUser);

		} else {

			request.setAttribute("updateError", "Profile update failed. Please try again.");

			request.setAttribute("user", user);
		}

		request.getRequestDispatcher("/profile.jsp").forward(request, response);
	}
}
