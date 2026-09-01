package com.movieticket.controller.movie;

import java.io.IOException;
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
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<MovieBean> movies = movieDAO.getAllMovies();

        request.setAttribute("movies", movies);

        request.getRequestDispatcher("/movie/movies.jsp")
               .forward(request, response);
    }
}