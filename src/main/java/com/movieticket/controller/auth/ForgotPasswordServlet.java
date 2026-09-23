package com.movieticket.controller.auth;

import com.movieticket.dao.UserDAO;
import com.movieticket.model.EmailVerificationBean;
import com.movieticket.model.UserBean;
import com.movieticket.util.EmailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.SecureRandom;

@WebServlet("/forgotpassword")
public class ForgotPasswordServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final long RESEND_TIME = 60 * 1000;

	private final SecureRandom random = new SecureRandom();

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

		/* SEND OTP */
		if ("send".equals(action)) {
			sendOtp(request, response);
			return;
		}

		/* VERIFY OTP */
		if ("verify".equals(action)) {
			verifyOtp(request, response);
			return;
		}

		/* RESEND OTP */
		if ("resend".equals(action)) {
			resendOtp(request, response);
			return;
		}

		/* OLD NORMAL REQUEST */
		String email = request.getParameter("email");

		if (email == null || email.trim().isEmpty()) {
			request.setAttribute("error", "Email address is required.");
			request.getRequestDispatcher("/forgotpassword.jsp").forward(request, response);
			return;
		}

		email = email.trim();

		UserBean user = userDAO.getUserByEmail(email);

		if (user != null) {

			HttpSession session = request.getSession();

			session.setAttribute("forgotUserId", user.getId());
			session.setAttribute("forgotUserEmail", user.getEmail());

			response.sendRedirect(request.getContextPath() + "/changepassword");

		} else {

			request.setAttribute("error", "No account is registered with this email address.");

			request.getRequestDispatcher("/forgotpassword.jsp").forward(request, response);
		}
	}

	/* SEND OTP */
	private void sendOtp(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String email = request.getParameter("email");

		if (email == null || email.trim().isEmpty()) {
			writeJson(response, false, "Email address is required.");
			return;
		}

		email = email.trim();

		UserBean user = userDAO.getUserByEmail(email);

		if (user == null) {
			writeJson(response, false, "No account is registered with this email address.");
			return;
		}

		String otp = generateOtp();

		long currentTime = System.currentTimeMillis();

		EmailVerificationBean verification = new EmailVerificationBean(user.getEmail(), otp, currentTime);

		HttpSession session = request.getSession();

		session.setAttribute("forgotPasswordVerification", verification);

		session.setAttribute("forgotUserId", user.getId());

		session.setAttribute("forgotUserEmail", user.getEmail());

		session.setAttribute("forgotPasswordVerified", false);

		try {

			EmailService.sendOtpEmail(user.getEmail(), otp);

			writeJson(response, true, "Verification code sent to your email.");

		} catch (Exception e) {

			session.removeAttribute("forgotPasswordVerification");

			session.removeAttribute("forgotPasswordVerified");

			e.printStackTrace();

			writeJson(response, false, "Unable to send verification code.");
		}
	}

	/* VERIFY OTP */
	private void verifyOtp(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String enteredOtp = request.getParameter("otp");

		HttpSession session = request.getSession(false);

		if (session == null) {

			writeJson(response, false, "Please request a verification code first.");

			return;
		}

		EmailVerificationBean verification = (EmailVerificationBean) session.getAttribute("forgotPasswordVerification");

		if (verification == null) {

			writeJson(response, false, "Please request a verification code first.");

			return;
		}

		/*
		 * SERVER-SIDE LOCK
		 *
		 * OTP has already been successfully verified. It cannot be verified again.
		 */
		Boolean alreadyVerified = (Boolean) session.getAttribute("forgotPasswordVerified");

		if (Boolean.TRUE.equals(alreadyVerified)) {

			writeJson(response, false, "Email is already verified.");

			return;
		}

		if (enteredOtp == null || enteredOtp.trim().isEmpty()) {

			writeJson(response, false, "Please enter the verification code.");

			return;
		}

		enteredOtp = enteredOtp.trim();

		if (!verification.getOtp().equals(enteredOtp)) {

			writeJson(response, false, "Verification code does not match.");

			return;
		}

		verification.setVerified(true);

		session.setAttribute("forgotPasswordVerification", verification);

		session.setAttribute("forgotPasswordVerified", true);

		writeJson(response, true, "Email verified successfully.");
	}

	/* RESEND OTP */
	private void resendOtp(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);

		if (session == null) {

			writeJson(response, false, "Please request a verification code first.");

			return;
		}

		/*
		 * SERVER-SIDE LOCK
		 *
		 * Once OTP verification succeeds, resend is no longer allowed.
		 *
		 * This protects against bypassing JavaScript.
		 */
		Boolean verified = (Boolean) session.getAttribute("forgotPasswordVerified");

		if (Boolean.TRUE.equals(verified)) {

			writeJson(response, false, "Email is already verified. Resend is not allowed.");

			return;
		}

		EmailVerificationBean oldVerification = (EmailVerificationBean) session
				.getAttribute("forgotPasswordVerification");

		if (oldVerification == null) {

			writeJson(response, false, "Please request a verification code first.");

			return;
		}

		long currentTime = System.currentTimeMillis();

		long elapsed = currentTime - oldVerification.getGeneratedAt();

		if (elapsed < RESEND_TIME) {

			long remaining = (RESEND_TIME - elapsed) / 1000;

			writeJson(response, false, "Please wait " + remaining + " seconds before requesting another code.");

			return;
		}

		String otp = generateOtp();

		EmailVerificationBean newVerification = new EmailVerificationBean(oldVerification.getEmail(), otp, currentTime);

		try {

			EmailService.sendOtpEmail(newVerification.getEmail(), otp);

			session.setAttribute("forgotPasswordVerification", newVerification);

			session.setAttribute("forgotPasswordVerified", false);

			writeJson(response, true, "New verification code sent.");

		} catch (Exception e) {

			e.printStackTrace();

			writeJson(response, false, "Unable to send verification code.");
		}
	}

	/* GENERATE 4 DIGIT OTP */
	private String generateOtp() {

		int number = 1000 + random.nextInt(9000);

		return String.valueOf(number);
	}

	/* JSON RESPONSE */
	private void writeJson(HttpServletResponse response, boolean success, String message) throws IOException {

		String safeMessage = message.replace("\\", "\\\\").replace("\"", "\\\"");

		response.getWriter().write("{\"success\":" + success + ",\"message\":\"" + safeMessage + "\"}");
	}
}