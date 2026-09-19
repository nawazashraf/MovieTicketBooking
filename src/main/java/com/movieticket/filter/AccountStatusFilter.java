package com.movieticket.filter;

import java.io.IOException;

import com.movieticket.dao.UserDAO;
import com.movieticket.model.UserBean;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AccountStatusFilter implements Filter {

	private UserDAO userDAO = new UserDAO();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String contextPath = req.getContextPath();

		String path = req.getRequestURI().substring(contextPath.length());

		HttpSession session = req.getSession(false);

		/*
		 * ============================================================ STATIC RESOURCES
		 * ============================================================
		 */

		if (path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/")
				|| path.startsWith("/img/") || path.startsWith("/fonts/") || path.startsWith("/assets/")
				|| path.startsWith("/static/") || path.startsWith("/webjars/") || path.endsWith(".css")
				|| path.endsWith(".js") || path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".jpeg")
				|| path.endsWith(".gif") || path.endsWith(".webp") || path.endsWith(".svg") || path.endsWith(".ico")
				|| path.endsWith(".woff") || path.endsWith(".woff2") || path.endsWith(".ttf")) {

			chain.doFilter(request, response);
			return;
		}

		/*
		 * ============================================================ USER NOT LOGGED
		 * IN ============================================================
		 */

		if (session == null || session.getAttribute("userId") == null) {

			chain.doFilter(request, response);
			return;
		}

		/*
		 * ============================================================ ALWAYS
		 * ACCESSIBLE PAGES ============================================================
		 */

		if (path.equals("/index.jsp") || path.equals("/movie-card.jsp") || path.equals("/booking-history.jsp")
				|| path.equals("/profile.jsp") || path.equals("/register.jsp") || path.equals("/login.jsp")
				|| path.equals("/ticket.jsp") || path.equals("/ticket") || path.equals("/login")
				|| path.equals("/logout") || path.equals("/profile") || path.equals("/register")
				|| path.equals("/bookings") || path.equals("/home") || path.equals("/movies/details")
				|| path.equals("/movies") || path.equals("/movies/search") || path.equals("/shows")) {

			chain.doFilter(request, response);
			return;
		}

		/*
		 * ============================================================ JSP INCLUDE /
		 * COMMON FILES ============================================================
		 */

		if (path.startsWith("/includes/") || path.startsWith("/common/") || path.startsWith("/components/")
				|| path.startsWith("/header/") || path.startsWith("/footer/")) {

			chain.doFilter(request, response);
			return;
		}

		/*
		 * ============================================================ GET USER ID
		 * ============================================================
		 */

		String userId = (String) session.getAttribute("userId");

		/*
		 * ============================================================ GET LATEST USER
		 * FROM DATABASE ============================================================
		 */

		UserBean user = userDAO.getUserById(userId);

		/*
		 * ============================================================ USER NOT FOUND
		 * ============================================================
		 */

		if (user == null) {

			session.invalidate();

			res.sendRedirect(contextPath + "/login.jsp");
			return;
		}

		/*
		 * ============================================================ UPDATE SESSION
		 * ============================================================
		 */

		session.setAttribute("user", user);
		session.setAttribute("userName", user.getName());
		session.setAttribute("userRole", user.getRole());

		/*
		 * ============================================================ STATUS = 0
		 * ============================================================
		 *
		 * User is inactive.
		 *
		 * Store a session flag so that the profile page knows that the user was
		 * redirected here because of an inactive account.
		 */

		if (!user.isStatus()) {

			session.setAttribute("accountInactive", true);

			res.sendRedirect(contextPath + "/profile");

			return;
		}

		/*
		 * ============================================================ STATUS = 1
		 * ============================================================
		 *
		 * Everything is allowed.
		 */

		chain.doFilter(request, response);
	}

}