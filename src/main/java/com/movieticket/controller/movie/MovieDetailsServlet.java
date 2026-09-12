package com.movieticket.controller.movie;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.movieticket.dao.MovieDAO;
import com.movieticket.model.MovieBean;

@WebServlet("/movies/details")
public class MovieDetailsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final MovieDAO movieDAO = new MovieDAO();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get movie ID from URL
		String movieId = request.getParameter("id");
		if (movieId == null || movieId.isBlank()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Movie ID is required");
			return;
		}

		MovieBean movie = movieDAO.getMovieById(movieId);

		if (movie == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Movie not found");
			return;
		}

		request.setAttribute("movie", movie);

		request.getRequestDispatcher("/movie/movie-details.jsp").forward(request, response);

	}
}