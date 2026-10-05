
package com.movieticket.controller.movie;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.movieticket.dao.MovieDAO;
import com.movieticket.model.MovieBean;

@WebServlet("/movies")
public class MovieServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final MovieDAO movieDAO = new MovieDAO();

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String status = request.getParameter("status");

            List<MovieBean> movies;

            if (status != null && !status.isBlank()) {

                movies = movieDAO.getMoviesByStatus(status);

            } else {

                movies = movieDAO.getAllMovies();
            }

            request.setAttribute("movies", movies);

            request.getRequestDispatcher("/movie/movies.jsp").forward(request, response);

        } catch (SQLException e) {

            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}