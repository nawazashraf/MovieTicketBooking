package com.movieticket.controller.home;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.movieticket.dao.MovieDAO;
import com.movieticket.model.MovieBean;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final MovieDAO movieDAO = new MovieDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("========== HOME PERFORMANCE ==========");

		// 1. NOW SHOWING
		long start = System.currentTimeMillis();

		List<MovieBean> nowShowingMovies = movieDAO.getMoviesByStatus("NOW_SHOWING");

		long end = System.currentTimeMillis();

		System.out.println("NOW_SHOWING DAO TIME: " + (end - start) + " ms");

		// 2. COMING SOON
		start = System.currentTimeMillis();

		List<MovieBean> comingSoonMovies = movieDAO.getMoviesByStatus("COMING_SOON");

		end = System.currentTimeMillis();

		System.out.println("COMING_SOON DAO TIME: " + (end - start) + " ms");

		// 3. Set attributes
		start = System.currentTimeMillis();

		request.setAttribute("nowShowingMovies", nowShowingMovies);

		request.setAttribute("comingSoonMovies", comingSoonMovies);

		end = System.currentTimeMillis();

		System.out.println("SET ATTRIBUTES TIME: " + (end - start) + " ms");

		// 4. JSP
		start = System.currentTimeMillis();

		request.getRequestDispatcher("/index.jsp").forward(request, response);

		end = System.currentTimeMillis();

		System.out.println("JSP FORWARD/RENDER TIME: " + (end - start) + " ms");

		System.out.println("======================================");
	}
}
