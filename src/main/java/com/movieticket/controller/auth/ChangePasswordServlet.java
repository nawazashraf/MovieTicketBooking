package com.movieticket.controller.auth;

import com.movieticket.dao.UserDAO;
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

@WebServlet("/changepassword")
public class ChangePasswordServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UserDAO userDAO;

	@Override
	public void init() {
		userDAO = new UserDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		/*
		 * ======================================== FORGOT PASSWORD FLOW
		 * ========================================
		 */

		if (session != null && session.getAttribute("forgotUserId") != null) {

			Boolean verified = (Boolean) session.getAttribute("forgotPasswordVerified");

			if (Boolean.TRUE.equals(verified)) {

				request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

				return;
			}

			response.sendRedirect(request.getContextPath() + "/forgotpassword");

			return;
		}

		/*
		 * ======================================== NORMAL LOGGED-IN CHANGE PASSWORD
		 * FLOW ========================================
		 */

		if (session == null || session.getAttribute("userId") == null) {

			response.sendRedirect(request.getContextPath() + "/login.jsp");

			return;
		}

		request.getRequestDispatcher("/changepassword.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		/*
		 * ======================================== FORGOT PASSWORD FLOW
		 * ========================================
		 */

		if (session != null && session.getAttribute("forgotUserId") != null) {

			Boolean verified = (Boolean) session.getAttribute("forgotPasswordVerified");

			/*
			 * OTP MUST BE VERIFIED
			 */

			if (!Boolean.TRUE.equals(verified)) {

				response.sendRedirect(request.getContextPath() + "/forgotpassword");

				return;
			}

			String userId = (String) session.getAttribute("forgotUserId");

			String newPassword = request.getParameter("newPassword");

			String confirmPassword = request.getParameter("confirmPassword");

			if (newPassword == null || confirmPassword == null || newPassword.isEmpty() || confirmPassword.isEmpty()) {

				request.setAttribute("error", "All fields are required.");

				request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

				return;
			}

			if (!newPassword.equals(confirmPassword)) {

				request.setAttribute("error", "New password and confirm password do not match.");

				request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

				return;
			}

			/*
			 * Same password validation style as your registration page
			 */

			if (newPassword.length() < 8) {

				request.setAttribute("error", "Password must contain at least 8 characters.");

				request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

				return;
			}

			boolean changed = userDAO.resetPassword(userId, newPassword);

			if (changed) {

				// Send password changed email ONLY after successful update

				String forgotEmail = (String) session.getAttribute("forgotUserEmail");

				String changedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));

				try {

					EmailService.sendPasswordChangedEmail(forgotEmail, "MovieBook User", changedAt);

				} catch (Exception e) {

					e.printStackTrace();

				}

				session.removeAttribute("forgotUserId");

				session.removeAttribute("forgotUserEmail");

				session.removeAttribute("forgotPasswordVerification");

				session.removeAttribute("forgotPasswordVerified");

				request.setAttribute("resetSuccess", "Your password has been changed successfully.");

				request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

			} else {

				request.setAttribute("error", "Password reset failed. Please try again.");

				request.getRequestDispatcher("/changepassword.jsp").forward(request, response);
			}

			return;
		}

		/*
		 * ======================================== NORMAL LOGGED-IN CHANGE PASSWORD
		 * FLOW ========================================
		 */

		if (session == null || session.getAttribute("userId") == null) {

			response.sendRedirect(request.getContextPath() + "/login.jsp");

			return;
		}

		String userId = (String) session.getAttribute("userId");

		String currentPassword = request.getParameter("currentPassword");

		String newPassword = request.getParameter("newPassword");

		String confirmPassword = request.getParameter("confirmPassword");

		if (currentPassword == null || newPassword == null || confirmPassword == null || currentPassword.isEmpty()
				|| newPassword.isEmpty() || confirmPassword.isEmpty()) {

			request.setAttribute("error", "All fields are required.");

			request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

			return;
		}

		if (!newPassword.equals(confirmPassword)) {

			request.setAttribute("error", "New password and confirm password do not match.");

			request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

			return;
		}

		boolean changed = userDAO.changePassword(userId, currentPassword, newPassword);

		if (changed) {

			// Send password changed email ONLY after successful update

			com.movieticket.model.UserBean user = (com.movieticket.model.UserBean) session.getAttribute("user");

			String changedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));

			if (user != null) {

				try {

					EmailService.sendPasswordChangedEmail(user.getEmail(), user.getName(), changedAt);

				} catch (Exception e) {

					e.printStackTrace();

				}
			}

			response.sendRedirect(request.getContextPath() + "/profile");

		} else {

			request.setAttribute("error", "Current password is incorrect.");

			request.getRequestDispatcher("/changepassword.jsp").forward(request, response);
		}

	}

}