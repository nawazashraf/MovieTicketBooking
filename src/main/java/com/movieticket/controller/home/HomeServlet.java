package com.movieticket.controller.home;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.movieticket.dao.MovieDAO;
import com.movieticket.model.MovieBean;

/** Loads the current and upcoming movies shown on the home page. */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final MovieDAO movieDAO = new MovieDAO();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<MovieBean> nowShowingMovies = movieDAO.getMoviesByStatus("NOW_SHOWING");
			List<MovieBean> comingSoonMovies = movieDAO.getMoviesByStatus("COMING_SOON");
			Map<String, String> genres = movieDAO.getAllGenres();
			Map<String, List<MovieBean>> moviesByGenre = new LinkedHashMap<>();

			for (String genreName : genres.values()) {
				moviesByGenre.put(genreName, new ArrayList<>());
			}

			List<MovieBean> allMovies = new ArrayList<>(nowShowingMovies);
			allMovies.addAll(comingSoonMovies);
			for (MovieBean movie : allMovies) {
				for (String genreId : movie.getGenreIds()) {
					String genreName = genres.get(genreId);
					if (genreName != null) {
						moviesByGenre.get(genreName).add(movie);
					}
				}
			}

			request.setAttribute("nowShowingMovies", nowShowingMovies);
			request.setAttribute("comingSoonMovies", comingSoonMovies);
			request.setAttribute("moviesByGenre", moviesByGenre);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} catch (Exception e) {
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
}
