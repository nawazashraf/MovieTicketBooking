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
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		List<MovieBean> nowShowingMovies = movieDAO.getMoviesByStatus("NOW_SHOWING");
		List<MovieBean> comingSoonMovies = movieDAO.getMoviesByStatus("COMING_SOON");

		request.setAttribute("nowShowingMovies", nowShowingMovies);
		request.setAttribute("comingSoonMovies", comingSoonMovies);

		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

}
