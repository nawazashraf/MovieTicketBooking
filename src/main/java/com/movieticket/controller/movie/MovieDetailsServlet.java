package com.movieticket.controller.movie;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.movieticket.dao.MovieDAO;
import com.movieticket.dao.ShowDAO;
import com.movieticket.model.MovieBean;
import com.movieticket.model.ShowBean;

@WebServlet("/movies/details")
public class MovieDetailsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final MovieDAO movieDAO = new MovieDAO();
	private final ShowDAO showDAO = new ShowDAO();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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

		List<ShowBean> shows = showDAO.getShowsByMovieId(movieId);

		Map<String, List<ShowBean>> showsByDate = new LinkedHashMap<String, List<ShowBean>>();

		for (ShowBean show : shows) {
			showsByDate.computeIfAbsent(show.getShowDate(), key -> new ArrayList<>()).add(show);
		}

		request.setAttribute("movie", movie);
		request.setAttribute("shows", shows);
		request.setAttribute("showsByDate", showsByDate);

		request.getRequestDispatcher("/movie/movie-details.jsp").forward(request, response);

	}
}