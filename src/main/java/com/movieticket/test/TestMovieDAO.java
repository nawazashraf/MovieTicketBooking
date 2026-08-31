package com.movieticket.test;

import java.util.List;

import com.movieticket.dao.MovieDAO;
import com.movieticket.model.MovieBean;

public class TestMovieDAO {

    public static void main(String[] args) {
        MovieDAO movieDAO = new MovieDAO();

        List<MovieBean> movies = movieDAO.getAllMovies();

        System.out.println("Total movies: " + movies.size());

        for (MovieBean movie : movies) {
            System.out.println(movie.getId()
                    + " | " + movie.getTitle()
                    + " | " + movie.getStatus()
                    + " | Genres: " + movie.getGenreIds());
        }
    }
}