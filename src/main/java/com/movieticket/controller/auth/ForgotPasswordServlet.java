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

		String action = request.getParameter("action");

		HttpSession session = request.getSession();

		/* SECURITY ANSWER VERIFICATION */

		if ("verifyAnswer".equals(action)) {

			String email = (String) session.getAttribute("forgotUserEmail");

			String securityAnswer = request.getParameter("securityAnswer");

			if (email == null) {

				request.setAttribute("error", "Please enter your registered email address.");

				request.getRequestDispatcher("/forgotpassword.jsp").forward(request, response);

				return;
			}

			if (securityAnswer == null || securityAnswer.trim().isEmpty()) {

				request.setAttribute("error", "Security answer is required.");

				request.setAttribute("email", email);

				String securityQuestion = userDAO.getSecurityQuestionByEmail(email);

				request.setAttribute("securityQuestion", securityQuestion);

				request.getRequestDispatcher("/forgotpassword.jsp").forward(request, response);

				return;
			}

			boolean answerCorrect = userDAO.verifySecurityAnswer(email, securityAnswer.trim());

			if (answerCorrect) {

				session.setAttribute("forgotPasswordVerified", true);

				response.sendRedirect(request.getContextPath() + "/changepassword");

				return;
			}

			request.setAttribute("error", "Incorrect security answer. Please try again.");

			request.setAttribute("email", email);

			String securityQuestion = userDAO.getSecurityQuestionByEmail(email);

			request.setAttribute("securityQuestion", securityQuestion);

			request.getRequestDispatcher("/forgotpassword.jsp").forward(request, response);

			return;
		}

		/* EMAIL VERIFICATION */

		String email = request.getParameter("email");

		if (email == null || email.trim().isEmpty()) {

			request.setAttribute("error", "Email address is required.");

			request.getRequestDispatcher("/forgotpassword.jsp").forward(request, response);

			return;
		}

		email = email.trim();

		UserBean user = userDAO.getUserByEmail(email);

		if (user != null) {

			String securityQuestion = userDAO.getSecurityQuestionByEmail(email);

			session.setAttribute("forgotUserId", user.getId());

			session.setAttribute("forgotUserEmail", user.getEmail());

			session.removeAttribute("forgotPasswordVerified");

			request.setAttribute("email", user.getEmail());

			request.setAttribute("securityQuestion", securityQuestion);

			request.getRequestDispatcher("/forgotpassword.jsp").forward(request, response);

		} else {

			request.setAttribute("error", "Please enter a valid registered email address.");

			request.setAttribute("email", email);

			request.getRequestDispatcher("/forgotpassword.jsp").forward(request, response);
		}
	}
}