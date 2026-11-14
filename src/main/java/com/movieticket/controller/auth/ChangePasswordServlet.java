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

/** Handles password changes for signed-in users and verified reset requests. */
@WebServlet("/changepassword")
public class ChangePasswordServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final UserDAO userDAO = new UserDAO();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("forgotUserId") != null) {

			Boolean verified = (Boolean) session.getAttribute("forgotPasswordVerified");

			if (Boolean.TRUE.equals(verified)) {

				request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);

				return;
			}

			response.sendRedirect(request.getContextPath() + "/forgotpassword");

			return;
		}

		if (session == null || session.getAttribute("userId") == null) {

			response.sendRedirect(request.getContextPath() + "/auth/login.jsp");

			return;
		}

		String userId = String.valueOf(session.getAttribute("userId"));

		UserBean user = userDAO.getUserById(userId);

		if (user == null || !user.isStatus()) {

			response.sendRedirect(request.getContextPath() + "/profile");

			return;
		}

		request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		if (session != null && session.getAttribute("forgotUserId") != null) {

			Boolean verified = (Boolean) session.getAttribute("forgotPasswordVerified");

			if (!Boolean.TRUE.equals(verified)) {

				response.sendRedirect(request.getContextPath() + "/forgotpassword");

				return;
			}

			String userId = String.valueOf(session.getAttribute("forgotUserId"));

			// Use the account ID saved in the verified session, not a form value.
			UserBean user = userDAO.getUserById(userId);

			if (user == null) {

				session.removeAttribute("forgotUserId");
				session.removeAttribute("forgotUserEmail");
				session.removeAttribute("forgotPasswordVerified");

				response.sendRedirect(request.getContextPath() + "/forgotpassword");

				return;
			}

			String newPassword = request.getParameter("newPassword");

			String confirmPassword = request.getParameter("confirmPassword");

			if (newPassword == null || confirmPassword == null || newPassword.isEmpty() || confirmPassword.isEmpty()) {

				request.setAttribute("error", "All fields are required.");

				request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);

				return;
			}

			if (!newPassword.equals(newPassword.trim())) {

				request.setAttribute("error", "Password must not contain leading or trailing spaces.");

				request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);

				return;
			}

			if (newPassword.length() < 8) {

				request.setAttribute("error", "Password must contain at least 8 characters.");

				request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);

				return;
			}

			if (newPassword.length() > 128) {

				request.setAttribute("error", "Password must not exceed 128 characters.");

				request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);

				return;
			}

			if (!newPassword.equals(confirmPassword)) {

				request.setAttribute("error", "New password and confirm password do not match.");

				request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);

				return;
			}

			boolean changed = userDAO.updatePassword(userId, newPassword);

			if (changed) {
				userDAO.activateAccount(userId);

				// Don't let the same verification authorize another reset.
				session.removeAttribute("forgotUserId");
				session.removeAttribute("forgotUserEmail");
				session.removeAttribute("forgotPasswordVerified");

				request.setAttribute("resetSuccess", "Your password has been changed successfully.");

				request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);

			} else {

				request.setAttribute("error", "Password reset failed. Please try again.");

				request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);
			}

			return;
		}

		if (session == null || session.getAttribute("userId") == null) {

			response.sendRedirect(request.getContextPath() + "/auth/login.jsp");

			return;
		}

		String userId = String.valueOf(session.getAttribute("userId"));

		UserBean user = userDAO.getUserById(userId);

		if (user == null || !user.isStatus()) {

			response.sendRedirect(request.getContextPath() + "/profile");

			return;
		}

		String newPassword = request.getParameter("newPassword");

		String confirmPassword = request.getParameter("confirmPassword");

		if (newPassword == null || confirmPassword == null || newPassword.isEmpty() || confirmPassword.isEmpty()) {

			request.setAttribute("error", "All fields are required.");

			request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);

			return;
		}

		if (!newPassword.equals(newPassword.trim())) {

			request.setAttribute("error", "Password must not contain leading or trailing spaces.");

			request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);

			return;
		}

		if (newPassword.length() < 8) {

			request.setAttribute("error", "Password must contain at least 8 characters.");

			request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);

			return;
		}

		if (newPassword.length() > 128) {

			request.setAttribute("error", "Password must not exceed 128 characters.");

			request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);

			return;
		}

		if (!newPassword.equals(confirmPassword)) {

			request.setAttribute("error", "New password and confirm password do not match.");

			request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);

			return;
		}

		boolean changed = userDAO.updatePassword(userId, newPassword);

		if (changed) {

			response.sendRedirect(request.getContextPath() + "/profile");

		} else {

			request.setAttribute("error", "Password change failed. Please try again.");

			request.getRequestDispatcher("/auth/changepassword.jsp").forward(request, response);
		}
	}
}