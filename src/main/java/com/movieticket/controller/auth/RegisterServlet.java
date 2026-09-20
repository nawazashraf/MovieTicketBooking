package com.movieticket.controller.auth;

import com.movieticket.dao.UserDAO;
import com.movieticket.model.UserBean;
import com.movieticket.util.EmailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

		// NAME VALIDATION AND FORMATTING
		if (name == null || name.trim().isEmpty()) {
			request.setAttribute("error", "Name is required.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		name = name.trim();

		// Only English letters and spaces
		if (!name.matches("[A-Za-z ]+")) {
			request.setAttribute("error", "Name can contain only letters and spaces.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		// Remove extra spaces between words
		name = name.replaceAll("\\s+", " ");

		// Minimum 2 characters
		if (name.length() < 2) {
			request.setAttribute("error", "Name must contain at least 2 characters.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		// First letter of each word capital
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

		// EMAIL
		if (email == null || email.trim().isEmpty()) {
			request.setAttribute("error", "Email is required.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		// Trim and always convert email to lowercase
		email = email.trim().toLowerCase();

		// EMAIL FORMAT
		String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

		if (!email.matches(emailRegex)) {
			request.setAttribute("error", "Please enter a valid email address.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		// PASSWORD
		if (password == null || password.isEmpty()) {
			request.setAttribute("error", "Password is required.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		// Password must be greater than 7 characters
		if (password.length() < 8) {
			request.setAttribute("error", "Password must be at least 8 characters.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		// PHONE
		if (phone == null || phone.isEmpty()) {
			request.setAttribute("error", "Phone number is required.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		// Exactly 10 digits
		if (!phone.matches("\\d{10}")) {
			request.setAttribute("error", "Phone number must contain exactly 10 digits.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
		}

		// CREATE USER
		UserBean user = new UserBean();

		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setPhone(phone);
		user.setRole("USER");
		user.setStatus(true);

		boolean registered = userDAO.registerUser(user);

		if (registered) {

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