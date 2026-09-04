package com.movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.movieticket.model.TheatreBean;
import com.movieticket.util.DBConnection;

public class TheatreDAO {

    public boolean addTheatre(TheatreBean theatre) {
        String sql = """
            INSERT INTO malls
            (id, name, address, city, state, pincode, status)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        if (theatre.getId() == null || theatre.getId().isBlank()) {
            theatre.setId(UUID.randomUUID().toString());
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, theatre.getId());
            ps.setString(2, theatre.getName());
            ps.setString(3, theatre.getAddress());
            ps.setString(4, theatre.getCity());
            ps.setString(5, theatre.getState());
            ps.setInt(6, theatre.getPincode());
            ps.setBoolean(7, theatre.isStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public TheatreBean getTheatreById(String theatreId) {
        String sql = "SELECT * FROM malls WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, theatreId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapTheatre(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<TheatreBean> getAllTheatres() {
        String sql = "SELECT * FROM malls ORDER BY name";
        List<TheatreBean> theatres = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                theatres.add(mapTheatre(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return theatres;
    }

    public boolean updateTheatre(TheatreBean theatre) {
        String sql = """
            UPDATE malls
            SET name = ?,
                address = ?,
                city = ?,
                state = ?,
                pincode = ?,
                status = ?
            WHERE id = ?
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, theatre.getName());
            ps.setString(2, theatre.getAddress());
            ps.setString(3, theatre.getCity());
            ps.setString(4, theatre.getState());
            ps.setInt(5, theatre.getPincode());
            ps.setBoolean(6, theatre.isStatus());
            ps.setString(7, theatre.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTheatre(String theatreId) {
        String sql = "DELETE FROM malls WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, theatreId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private TheatreBean mapTheatre(ResultSet rs) throws Exception {
        TheatreBean theatre = new TheatreBean();

        theatre.setId(rs.getString("id"));
        theatre.setName(rs.getString("name"));
        theatre.setAddress(rs.getString("address"));
        theatre.setCity(rs.getString("city"));
        theatre.setState(rs.getString("state"));
        theatre.setPincode(rs.getInt("pincode"));
        theatre.setStatus(rs.getBoolean("status"));
        theatre.setCreatedAt(rs.getTimestamp("created_at"));

        return theatre;
    }
}