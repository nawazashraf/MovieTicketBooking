package com.movieticket.controller.auth;

import java.io.IOException;
import java.security.SecureRandom;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.movieticket.dao.UserDAO;
import com.movieticket.model.EmailVerificationBean;
import com.movieticket.model.UserBean;
import com.movieticket.util.EmailService;

@WebServlet("/emailVerification")
public class EmailVerificationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final long RESEND_TIME = 60 * 1000;

	private final SecureRandom random = new SecureRandom();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		if ("send".equals(action)) {

			sendOtp(request, response);

		} else if ("verify".equals(action)) {

			verifyOtp(request, response);

		} else if ("resend".equals(action)) {

			resendOtp(request, response);

		} else if ("activateSend".equals(action)) {

			activateSend(request, response);

		} else if ("activateVerify".equals(action)) {

			activateVerify(request, response);

		} else if ("activateResend".equals(action)) {

			activateResend(request, response);

		} else {

			response.getWriter().write("{\"success\":false,\"message\":\"Invalid request.\"}");
		}
	}

	// ==================== SEND OTP ====================

	private void sendOtp(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String email = request.getParameter("email");

		if (email == null || email.trim().isEmpty()) {

			response.getWriter().write("{\"success\":false,\"message\":\"Email is required.\"}");

			return;
		}

		email = email.trim();

		String otp = generateOtp();

		long currentTime = System.currentTimeMillis();

		EmailVerificationBean verification = new EmailVerificationBean(email, otp, currentTime);

		HttpSession session = request.getSession();

		session.setAttribute("emailVerification", verification);

		session.setAttribute("emailVerificationPurpose", "normal");

		try {

			EmailService.sendOtpEmail(email, otp);

			response.getWriter().write("{\"success\":true,\"message\":\"Verification code sent to your email.\"}");

		} catch (Exception e) {

			session.removeAttribute("emailVerification");

			session.removeAttribute("emailVerificationPurpose");

			e.printStackTrace();

			response.getWriter().write("{\"success\":false,\"message\":\"Unable to send verification code.\"}");
		}
	}

	// ==================== VERIFY OTP ====================

	private void verifyOtp(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String enteredOtp = request.getParameter("otp");

		HttpSession session = request.getSession(false);

		if (session == null) {

			response.getWriter().write("{\"success\":false,\"message\":\"Please request a verification code first.\"}");

			return;
		}

		EmailVerificationBean verification = (EmailVerificationBean) session.getAttribute("emailVerification");

		String purpose = (String) session.getAttribute("emailVerificationPurpose");

		if (verification == null || !"normal".equals(purpose)) {

			response.getWriter().write("{\"success\":false,\"message\":\"Please request a verification code first.\"}");

			return;
		}

		if (verification.isVerified()) {

			response.getWriter().write("{\"success\":false,\"message\":\"Email is already verified.\"}");

			return;
		}

		if (enteredOtp == null || enteredOtp.trim().isEmpty()) {

			response.getWriter().write("{\"success\":false,\"message\":\"Please enter the verification code.\"}");

			return;
		}

		enteredOtp = enteredOtp.trim();

		if (!verification.getOtp().equals(enteredOtp)) {

			response.getWriter().write("{\"success\":false,\"message\":\"Verification code does not match.\"}");

			return;
		}

		verification.setVerified(true);

		session.setAttribute("emailVerification", verification);

		response.getWriter().write("{\"success\":true,\"message\":\"Email verified successfully.\"}");
	}

	// ==================== RESEND OTP ====================

	private void resendOtp(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(false);

		if (session == null) {

			response.getWriter().write("{\"success\":false,\"message\":\"Please request a verification code first.\"}");

			return;
		}

		EmailVerificationBean oldVerification = (EmailVerificationBean) session.getAttribute("emailVerification");

		String purpose = (String) session.getAttribute("emailVerificationPurpose");

		if (oldVerification == null || !"normal".equals(purpose)) {

			response.getWriter().write("{\"success\":false,\"message\":\"Please request a verification code first.\"}");

			return;
		}

		/*
		 * SERVER-SIDE LOCK
		 *
		 * Even if the user bypasses JavaScript, a verified email cannot request another
		 * OTP.
		 */
		if (oldVerification.isVerified()) {

			response.getWriter().write(
					"{\"success\":false,\"message\":\"Email is already verified. You cannot request another verification code.\"}");

			return;
		}

		long currentTime = System.currentTimeMillis();

		long elapsed = currentTime - oldVerification.getGeneratedAt();

		if (elapsed < RESEND_TIME) {

			long remaining = (RESEND_TIME - elapsed) / 1000;

			response.getWriter().write("{\"success\":false,\"message\":\"Please wait " + remaining
					+ " seconds before requesting another code.\"}");

			return;
		}

		String otp = generateOtp();

		EmailVerificationBean newVerification = new EmailVerificationBean(oldVerification.getEmail(), otp, currentTime);

		try {

			EmailService.sendOtpEmail(newVerification.getEmail(), otp);

			session.setAttribute("emailVerification", newVerification);

			session.setAttribute("emailVerificationPurpose", "normal");

			response.getWriter().write("{\"success\":true,\"message\":\"New verification code sent.\"}");

		} catch (Exception e) {

			e.printStackTrace();

			response.getWriter().write("{\"success\":false,\"message\":\"Unable to send verification code.\"}");
		}
	}

	// ==================== ACCOUNT ACTIVATION - SEND OTP ====================

	private void activateSend(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("userId") == null) {

			response.getWriter().write("{\"success\":false,\"message\":\"Please login first.\"}");

			return;
		}

		String userId = String.valueOf(session.getAttribute("userId"));

		UserDAO userDAO = new UserDAO();

		UserBean user = userDAO.getUserById(userId);

		if (user == null) {

			response.getWriter().write("{\"success\":false,\"message\":\"User not found.\"}");

			return;
		}

		/*
		 * Account must actually be inactive.
		 */
		if (user.isStatus()) {

			response.getWriter().write("{\"success\":false,\"message\":\"Account is already active.\"}");

			return;
		}

		String email = user.getEmail();

		if (email == null || email.trim().isEmpty()) {

			response.getWriter().write("{\"success\":false,\"message\":\"Registered email not found.\"}");

			return;
		}

		email = email.trim();

		String otp = generateOtp();

		long currentTime = System.currentTimeMillis();

		EmailVerificationBean verification = new EmailVerificationBean(email, otp, currentTime);

		try {

			EmailService.sendOtpEmail(email, otp);

			session.setAttribute("emailVerification", verification);

			session.setAttribute("emailVerificationPurpose", "activation");

			response.getWriter()
					.write("{\"success\":true,\"message\":\"Verification code sent to your registered email.\"}");

		} catch (Exception e) {

			e.printStackTrace();

			response.getWriter().write("{\"success\":false,\"message\":\"Unable to send verification code.\"}");
		}
	}

	// ==================== ACCOUNT ACTIVATION - VERIFY OTP ====================

	private void activateVerify(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("userId") == null) {

			response.getWriter().write("{\"success\":false,\"message\":\"Please login first.\"}");

			return;
		}

		String enteredOtp = request.getParameter("otp");

		if (enteredOtp == null || enteredOtp.trim().isEmpty()) {

			response.getWriter().write("{\"success\":false,\"message\":\"Please enter the verification code.\"}");

			return;
		}

		EmailVerificationBean verification = (EmailVerificationBean) session.getAttribute("emailVerification");

		String purpose = (String) session.getAttribute("emailVerificationPurpose");

		/*
		 * SERVER-SIDE PURPOSE CHECK
		 *
		 * An OTP generated for registration/normal verification cannot be used to
		 * activate an account.
		 */
		if (verification == null || !"activation".equals(purpose)) {

			response.getWriter()
					.write("{\"success\":false,\"message\":\"Please request an activation verification code first.\"}");

			return;
		}

		/*
		 * SERVER-SIDE ONE-TIME VERIFICATION LOCK
		 */
		if (verification.isVerified()) {

			response.getWriter().write("{\"success\":false,\"message\":\"Verification code has already been used.\"}");

			return;
		}

		String userId = String.valueOf(session.getAttribute("userId"));

		UserDAO userDAO = new UserDAO();

		UserBean currentUser = userDAO.getUserById(userId);

		if (currentUser == null) {

			response.getWriter().write("{\"success\":false,\"message\":\"User not found.\"}");

			return;
		}

		/*
		 * Account must still be inactive.
		 */
		if (currentUser.isStatus()) {

			response.getWriter().write("{\"success\":false,\"message\":\"Account is already active.\"}");

			return;
		}

		/*
		 * OTP must belong to the logged-in user's registered email.
		 */
		if (verification.getEmail() == null || currentUser.getEmail() == null
				|| !verification.getEmail().equalsIgnoreCase(currentUser.getEmail())) {

			response.getWriter().write("{\"success\":false,\"message\":\"Invalid verification request.\"}");

			return;
		}

		enteredOtp = enteredOtp.trim();

		if (!verification.getOtp().equals(enteredOtp)) {

			response.getWriter().write("{\"success\":false,\"message\":\"Verification code does not match.\"}");

			return;
		}

		boolean activated = userDAO.activateAccount(userId);

		if (!activated) {

			response.getWriter().write("{\"success\":false,\"message\":\"Unable to activate account.\"}");

			return;
		}

		verification.setVerified(true);

		UserBean updatedUser = userDAO.getUserById(userId);

		if (updatedUser != null) {

			session.setAttribute("user", updatedUser);

			session.setAttribute("userName", updatedUser.getName());

			session.setAttribute("userRole", updatedUser.getRole());

			try {

				EmailService.sendAccountActivatedEmail(updatedUser.getEmail(), updatedUser.getName());

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		session.removeAttribute("emailVerification");

		session.removeAttribute("emailVerificationPurpose");

		session.setAttribute("accountInactive", false);

		response.getWriter().write("{\"success\":true,\"message\":\"Account activated successfully.\"}");
	}

	// ==================== ACCOUNT ACTIVATION - RESEND OTP ====================

	private void activateResend(HttpServletRequest request, HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("userId") == null) {

			response.getWriter().write("{\"success\":false,\"message\":\"Please login first.\"}");

			return;
		}

		String userId = String.valueOf(session.getAttribute("userId"));

		UserDAO userDAO = new UserDAO();

		UserBean user = userDAO.getUserById(userId);

		if (user == null) {

			response.getWriter().write("{\"success\":false,\"message\":\"User not found.\"}");

			return;
		}

		if (user.isStatus()) {

			response.getWriter().write("{\"success\":false,\"message\":\"Account is already active.\"}");

			return;
		}

		String email = user.getEmail();

		if (email == null || email.trim().isEmpty()) {

			response.getWriter().write("{\"success\":false,\"message\":\"Registered email not found.\"}");

			return;
		}

		email = email.trim();

		EmailVerificationBean oldVerification = (EmailVerificationBean) session.getAttribute("emailVerification");

		String purpose = (String) session.getAttribute("emailVerificationPurpose");

		/*
		 * Only an activation OTP can be resent here.
		 */
		if (oldVerification == null || !"activation".equals(purpose)) {

			response.getWriter().write("{\"success\":false,\"message\":\"Please request a verification code first.\"}");

			return;
		}

		/*
		 * OTP must belong to this user's email.
		 */
		if (oldVerification.getEmail() == null || !oldVerification.getEmail().equalsIgnoreCase(email)) {

			response.getWriter().write("{\"success\":false,\"message\":\"Invalid verification request.\"}");

			return;
		}

		/*
		 * If activation OTP was already verified, resend is permanently rejected by the
		 * server.
		 */
		if (oldVerification.isVerified()) {

			response.getWriter().write(
					"{\"success\":false,\"message\":\"Email is already verified. You cannot request another verification code.\"}");

			return;
		}

		long currentTime = System.currentTimeMillis();

		long elapsed = currentTime - oldVerification.getGeneratedAt();

		if (elapsed < RESEND_TIME) {

			long remaining = (RESEND_TIME - elapsed) / 1000;

			response.getWriter().write("{\"success\":false,\"message\":\"Please wait " + remaining
					+ " seconds before requesting another code.\"}");

			return;
		}

		String otp = generateOtp();

		EmailVerificationBean newVerification = new EmailVerificationBean(email, otp, currentTime);

		try {

			EmailService.sendOtpEmail(email, otp);

			session.setAttribute("emailVerification", newVerification);

			session.setAttribute("emailVerificationPurpose", "activation");

			response.getWriter().write("{\"success\":true,\"message\":\"New verification code sent.\"}");

		} catch (Exception e) {

			e.printStackTrace();

			response.getWriter().write("{\"success\":false,\"message\":\"Unable to send verification code.\"}");
		}
	}

	// ==================== OTP GENERATOR ====================

	private String generateOtp() {

		int number = 1000 + random.nextInt(9000);

		return String.valueOf(number);
	}
}