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

        // Format The Name
		if (name != null) {

			name = name.trim();

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
		}

		// Always save email in lowercase
		if (email != null) {
			email = email.trim().toLowerCase();
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