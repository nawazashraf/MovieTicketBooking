package com.movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.movieticket.model.ShowBean;
import com.movieticket.util.DBConnection;

public class ShowDAO {

    public boolean addShow(ShowBean show) {
        String sql = """
            INSERT INTO shows
            (id, movie_id, mall_id, show_date, start_time, end_time, status)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        if (show.getShowId() == null || show.getShowId().isBlank()) {
            show.setShowId(UUID.randomUUID().toString());
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setInsertValues(ps, show);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ShowBean getShowById(String showId) {
        String sql = """
            SELECT sh.*, m.title AS movie_name, ma.name AS mall_name
            FROM shows sh
            JOIN movies m ON sh.movie_id = m.id
            JOIN malls ma ON sh.mall_id = ma.id
            WHERE sh.id = ?
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, showId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapShow(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<ShowBean> getAllShows() {
        String sql = """
            SELECT sh.*, m.title AS movie_name, ma.name AS mall_name
            FROM shows sh
            JOIN movies m ON sh.movie_id = m.id
            JOIN malls ma ON sh.mall_id = ma.id
            ORDER BY sh.show_date, sh.start_time
            """;

        List<ShowBean> shows = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                shows.add(mapShow(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return shows;
    }

    public boolean updateShow(ShowBean show) {
        String sql = """
            UPDATE shows
            SET movie_id = ?,
                mall_id = ?,
                show_date = ?,
                start_time = ?,
                end_time = ?,
                status = ?
            WHERE id = ?
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, show.getMovieId());
            ps.setString(2, show.getMallId());
            ps.setString(3, show.getShowDate());
            ps.setString(4, show.getStartTime());
            ps.setString(5, show.getEndTime());
            ps.setString(6, show.getStatus());
            ps.setString(7, show.getShowId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteShow(String showId) {
        String sql = "DELETE FROM shows WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, showId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private ShowBean mapShow(ResultSet rs) throws Exception {
        ShowBean show = new ShowBean();

        show.setShowId(rs.getString("id"));
        show.setMovieId(rs.getString("movie_id"));
        show.setMallId(rs.getString("mall_id"));
        show.setMovieName(rs.getString("movie_name"));
        show.setMallName(rs.getString("mall_name"));
        show.setShowDate(rs.getString("show_date"));
        show.setStartTime(rs.getString("start_time"));
        show.setEndTime(rs.getString("end_time"));
        show.setStatus(rs.getString("status"));
        show.setCreatedAt(rs.getTimestamp("created_at"));

        return show;
    }

    private void setInsertValues(PreparedStatement ps, ShowBean show)
            throws Exception {

        ps.setString(1, show.getShowId());
        ps.setString(2, show.getMovieId());
        ps.setString(3, show.getMallId());
        ps.setString(4, show.getShowDate());
        ps.setString(5, show.getStartTime());
        ps.setString(6, show.getEndTime());
        ps.setString(7, show.getStatus());
    }
}