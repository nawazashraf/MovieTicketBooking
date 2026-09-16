package com.movieticket.controller.movie;

import java.io.IOException;
import java.util.List;

import com.movieticket.dao.MovieDAO;
import com.movieticket.model.MovieBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/movies/search")
public class SearchMovieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final MovieDAO movieDAO = new MovieDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("search");
        if (query == null) query = "";
        query = query.trim();

        List<MovieBean> movies = query.isBlank() ? movieDAO.getAllMovies() : movieDAO.searchMoviesByTitle(query);
        request.setAttribute("movies", movies);
        request.setAttribute("searchQuery", query);
        request.getRequestDispatcher("/movie/movies.jsp").forward(request, response);
    }
}