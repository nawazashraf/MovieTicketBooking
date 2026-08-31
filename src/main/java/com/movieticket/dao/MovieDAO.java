package com.movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.movieticket.model.MovieBean;
import com.movieticket.util.DBConnection;

public class MovieDAO {
	public boolean addMovie(MovieBean movie) {
        String sql = """
            INSERT INTO movies (
                id, title, description, duration_minutes, language,
                release_date, certificate, poster_url, trailer_url, status
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                if (movie.getId() == null || movie.getId().isBlank()) {
                    movie.setId(UUID.randomUUID().toString());
                }

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    setInsertValues(ps, movie);
                    ps.executeUpdate();
                }

                insertMovieGenres(conn, movie.getId(), movie.getGenreIds());

                conn.commit();
                return true;

            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public MovieBean getMovieById(String movieId) {
        String sql = "SELECT * FROM movies WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, movieId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    MovieBean movie = mapMovie(rs);
                    movie.setGenreIds(getGenreIds(conn, movieId));
                    return movie;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<MovieBean> getAllMovies() {
        String sql = "SELECT * FROM movies ORDER BY created_at DESC";
        List<MovieBean> movies = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MovieBean movie = mapMovie(rs);
                movie.setGenreIds(getGenreIds(conn, movie.getId()));
                movies.add(movie);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

    public boolean updateMovie(MovieBean movie) {
        String sql = """
            UPDATE movies
            SET title = ?,
                description = ?,
                duration_minutes = ?,
                language = ?,
                release_date = ?,
                certificate = ?,
                poster_url = ?,
                trailer_url = ?,
                status = ?
            WHERE id = ?
            """;

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    setUpdateValues(ps, movie);

                    if (ps.executeUpdate() == 0) {
                        conn.rollback();
                        return false;
                    }
                }

                try (PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM movie_genres WHERE movie_id = ?")) {
                    ps.setString(1, movie.getId());
                    ps.executeUpdate();
                }

                insertMovieGenres(conn, movie.getId(), movie.getGenreIds());

                conn.commit();
                return true;

            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMovie(String movieId) {
        String sql = "DELETE FROM movies WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, movieId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void insertMovieGenres(Connection conn, String movieId,
                                   List<String> genreIds) throws SQLException {
        if (genreIds == null || genreIds.isEmpty()) {
            return;
        }

        String sql = """
            INSERT INTO movie_genres (movie_id, genre_id)
            VALUES (?, ?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (String genreId : genreIds) {
                if (genreId != null && !genreId.isBlank()) {
                    ps.setString(1, movieId);
                    ps.setString(2, genreId);
                    ps.addBatch();
                }
            }

            ps.executeBatch();
        }
    }

    private List<String> getGenreIds(Connection conn, String movieId)
            throws SQLException {
        List<String> genreIds = new ArrayList<>();

        String sql = """
            SELECT genre_id
            FROM movie_genres
            WHERE movie_id = ?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, movieId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    genreIds.add(rs.getString("genre_id"));
                }
            }
        }

        return genreIds;
    }

    private MovieBean mapMovie(ResultSet rs) throws SQLException {
        MovieBean movie = new MovieBean();

        movie.setId(rs.getString("id"));
        movie.setTitle(rs.getString("title"));
        movie.setDescription(rs.getString("description"));
        movie.setDurationMinutes(rs.getInt("duration_minutes"));
        movie.setLanguage(rs.getString("language"));
        movie.setReleaseDate(rs.getDate("release_date"));
        movie.setCertificate(rs.getString("certificate"));
        movie.setPosterUrl(rs.getString("poster_url"));
        movie.setTrailerUrl(rs.getString("trailer_url"));
        movie.setStatus(rs.getString("status"));
        movie.setCreatedAt(rs.getTimestamp("created_at"));

        return movie;
    }

    private void setInsertValues(PreparedStatement ps, MovieBean movie)
            throws SQLException {
        ps.setString(1, movie.getId());
        ps.setString(2, movie.getTitle());
        ps.setString(3, movie.getDescription());
        ps.setInt(4, movie.getDurationMinutes());
        ps.setString(5, movie.getLanguage());
        ps.setDate(6, movie.getReleaseDate());
        ps.setString(7, movie.getCertificate());
        ps.setString(8, movie.getPosterUrl());
        ps.setString(9, movie.getTrailerUrl());
        ps.setString(10, movie.getStatus());
    }

    private void setUpdateValues(PreparedStatement ps, MovieBean movie)
            throws SQLException {
        ps.setString(1, movie.getTitle());
        ps.setString(2, movie.getDescription());
        ps.setInt(3, movie.getDurationMinutes());
        ps.setString(4, movie.getLanguage());
        ps.setDate(5, movie.getReleaseDate());
        ps.setString(6, movie.getCertificate());
        ps.setString(7, movie.getPosterUrl());
        ps.setString(8, movie.getTrailerUrl());
        ps.setString(9, movie.getStatus());
        ps.setString(10, movie.getId());
    }
}