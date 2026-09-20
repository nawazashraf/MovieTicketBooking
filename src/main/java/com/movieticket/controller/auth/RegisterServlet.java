
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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;

	@Override
	public void init() {
		userDAO = new UserDAO();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");

		if (name == null || name.trim().isEmpty()) {
			request.setAttribute("error", "Name is required.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		name = name.trim();

		if (!name.matches("[A-Za-z ]+")) {
			request.setAttribute("error", "Name can contain only letters and spaces.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		name = name.replaceAll("\\s+", " ");

		if (name.length() < 2 || name.length() > 50) {
			request.setAttribute("error", "Name must be between 2 and 50 characters.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

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

		if (email == null || email.trim().isEmpty()) {
			request.setAttribute("error", "Email is required.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		email = email.trim().toLowerCase();

		String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

		if (!email.matches(emailRegex)) {
			request.setAttribute("error", "Please enter a valid email address.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		/*
		 * SERVER-SIDE EMAIL OTP VERIFICATION
		 */
		HttpSession session = request.getSession(false);

		if (session == null) {
			request.setAttribute("error", "Please verify your email before creating an account.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		EmailVerificationBean verification = (EmailVerificationBean) session.getAttribute("emailVerification");

		if (verification == null || !verification.isVerified() || !email.equalsIgnoreCase(verification.getEmail())) {

			request.setAttribute("error", "Please verify your email before creating an account.");

			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		if (password == null || password.isEmpty()) {
			request.setAttribute("error", "Password is required.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		if (!password.equals(password.trim())) {
			request.setAttribute("error", "Password must not contain leading or trailing spaces.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		if (password.length() < 8) {
			request.setAttribute("error", "Password must contain at least 8 characters.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		if (password.length() > 128) {
			request.setAttribute("error", "Password cannot exceed 128 characters.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		if (phone == null || phone.trim().isEmpty()) {
			request.setAttribute("error", "Phone number is required.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		phone = phone.trim();

		if (!phone.matches("\\d{10}")) {
			request.setAttribute("error", "Phone number must contain exactly 10 digits.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		if (phone.startsWith("0")) {
			request.setAttribute("error", "Please enter a valid 10-digit mobile number.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		UserBean user = new UserBean();

		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setPhone(phone);
		user.setRole("USER");
		user.setStatus(true);

		boolean registered = userDAO.registerUser(user);

		if (registered) {

			session.removeAttribute("emailVerification");

			try {
				EmailService.sendWelcomeEmail(email, name, request.getContextPath());
			} catch (Exception e) {
				e.printStackTrace();
			}

			response.sendRedirect(request.getContextPath() + "/register.jsp?success=1");

		} else {

			request.setAttribute("error", "Registration failed. Email or phone may already exist.");

			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect(request.getContextPath() + "/register.jsp");
	}
}
