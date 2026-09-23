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
		 * FORGOT PASSWORD FLOW
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
		 * NORMAL LOGGED-IN FLOW
		 */
		if (session == null || session.getAttribute("userId") == null) {

			response.sendRedirect(request.getContextPath() + "/login.jsp");

			return;
		}

		String userId = String.valueOf(session.getAttribute("userId"));

		UserBean user = userDAO.getUserById(userId);

		/*
		 * INACTIVE USER CANNOT CHANGE PASSWORD
		 */
		if (user == null || !user.isStatus()) {

			response.sendRedirect(request.getContextPath() + "/profile");

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
			 * SERVER-SIDE OTP VERIFICATION CHECK
			 */
			if (!Boolean.TRUE.equals(verified)) {

				response.sendRedirect(request.getContextPath() + "/forgotpassword");

				return;
			}

			String userId = String.valueOf(session.getAttribute("forgotUserId"));

			/*
			 * GET USER AGAIN FROM DATABASE
			 *
			 * Do not trust user information supplied by browser.
			 */
			UserBean user = userDAO.getUserById(userId);

			if (user == null) {

				session.removeAttribute("forgotUserId");
				session.removeAttribute("forgotUserEmail");
				session.removeAttribute("forgotPasswordVerification");
				session.removeAttribute("forgotPasswordVerified");

				response.sendRedirect(request.getContextPath() + "/forgotpassword");

				return;
			}

			String newPassword = request.getParameter("newPassword");

			String confirmPassword = request.getParameter("confirmPassword");

			/*
			 * REQUIRED
			 */
			if (newPassword == null || confirmPassword == null || newPassword.isEmpty() || confirmPassword.isEmpty()) {

				request.setAttribute("error", "All fields are required.");

				request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

				return;
			}

			/*
			 * NO LEADING OR TRAILING SPACES
			 */
			if (!newPassword.equals(newPassword.trim())) {

				request.setAttribute("error", "Password must not contain leading or trailing spaces.");

				request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

				return;
			}

			/*
			 * PASSWORD LENGTH
			 */
			if (newPassword.length() < 8) {

				request.setAttribute("error", "Password must contain at least 8 characters.");

				request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

				return;
			}

			if (newPassword.length() > 128) {

				request.setAttribute("error", "Password must not exceed 128 characters.");

				request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

				return;
			}

			/*
			 * CONFIRM PASSWORD
			 */
			if (!newPassword.equals(confirmPassword)) {

				request.setAttribute("error", "New password and confirm password do not match.");

				request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

				return;
			}

			/*
			 * UPDATE PASSWORD
			 */
			boolean changed = userDAO.updatePassword(userId, newPassword);

			if (changed) {

				/*
				 * FORGOT PASSWORD SUCCESS ALSO ACTIVATE ACCOUNT
				 */
				userDAO.activateAccount(userId);

				String forgotEmail = (String) session.getAttribute("forgotUserEmail");

				String changedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));

				try {

					EmailService.sendPasswordChangedEmail(forgotEmail, "MovieBook User", changedAt);

				} catch (Exception e) {

					e.printStackTrace();
				}

				/*
				 * CONSUME FORGOT PASSWORD AUTHORIZATION
				 *
				 * This prevents the same verified session from being reused after the password
				 * reset.
				 */
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
		 * ======================================== NORMAL LOGGED-IN ACTIVE USER
		 * ========================================
		 */

		if (session == null || session.getAttribute("userId") == null) {

			response.sendRedirect(request.getContextPath() + "/login.jsp");

			return;
		}

		String userId = String.valueOf(session.getAttribute("userId"));

		UserBean user = userDAO.getUserById(userId);

		/*
		 * ONLY ACTIVE USERS CAN CHANGE PASSWORD
		 */
		if (user == null || !user.isStatus()) {

			response.sendRedirect(request.getContextPath() + "/profile");

			return;
		}

		/*
		 * PASSWORD
		 */
		String newPassword = request.getParameter("newPassword");

		String confirmPassword = request.getParameter("confirmPassword");

		/*
		 * REQUIRED
		 */
		if (newPassword == null || confirmPassword == null || newPassword.isEmpty() || confirmPassword.isEmpty()) {

			request.setAttribute("error", "All fields are required.");

			request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

			return;
		}

		/*
		 * NO LEADING OR TRAILING SPACES
		 */
		if (!newPassword.equals(newPassword.trim())) {

			request.setAttribute("error", "Password must not contain leading or trailing spaces.");

			request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

			return;
		}

		/*
		 * PASSWORD LENGTH
		 */
		if (newPassword.length() < 8) {

			request.setAttribute("error", "Password must contain at least 8 characters.");

			request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

			return;
		}

		if (newPassword.length() > 128) {

			request.setAttribute("error", "Password must not exceed 128 characters.");

			request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

			return;
		}

		/*
		 * CONFIRM PASSWORD
		 */
		if (!newPassword.equals(confirmPassword)) {

			request.setAttribute("error", "New password and confirm password do not match.");

			request.getRequestDispatcher("/changepassword.jsp").forward(request, response);

			return;
		}

		/*
		 * CHANGE PASSWORD DIRECTLY
		 */
		boolean changed = userDAO.updatePassword(userId, newPassword);

		if (changed) {

			String changedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));

			try {

				EmailService.sendPasswordChangedEmail(user.getEmail(), user.getName(), changedAt);

			} catch (Exception e) {

				e.printStackTrace();
			}

			response.sendRedirect(request.getContextPath() + "/profile");

		} else {

			request.setAttribute("error", "Password change failed. Please try again.");

			request.getRequestDispatcher("/changepassword.jsp").forward(request, response);
		}
	}
}