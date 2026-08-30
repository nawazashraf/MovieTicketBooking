package com.movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.movieticket.model.ShowBean;
import com.movieticket.model.SeatBean;
import com.movieticket.util.DBConnection;

public class SeatSelection {

	// =========================================================
	// GET SHOW DETAILS
	// =========================================================

	public ShowBean getShowDetails(String showId) throws Exception {

		ShowBean show = null;

		String sql = "SELECT sh.id, m.title, ma.name, " + "sh.show_date, sh.start_time " + "FROM shows sh "
				+ "JOIN movies m ON sh.movie_id = m.id " + "JOIN malls ma ON sh.mall_id = ma.id " + "WHERE sh.id = ?";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, showId);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {

					show = new ShowBean();

					show.setShowId(rs.getString("id"));
					show.setMovieName(rs.getString("title"));
					show.setMallName(rs.getString("name"));
					show.setShowDate(rs.getString("show_date"));
					show.setStartTime(rs.getString("start_time"));
				}
			}
		}

		return show;
	}

	// =========================================================
	// GET SEATS FOR SELECTED SHOW
	// =========================================================

	public ArrayList<SeatBean> getSeatsByShowId(String showId) throws Exception {

		ArrayList<SeatBean> seats = new ArrayList<>();

		String sql = "SELECT ss.id, " + "s.row_name, " + "s.seat_number, " + "st.type_name, " + "ss.price, "
				+ "ss.status " + "FROM show_seats ss " + "JOIN seats s ON ss.seat_id = s.id "
				+ "JOIN seat_types st ON s.seat_type_id = st.id " + "WHERE ss.show_id = ? "
				+ "ORDER BY s.row_name, s.seat_number";

		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, showId);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					SeatBean seat = new SeatBean();

					seat.setShowSeatId(rs.getString("id"));

					seat.setRowName(rs.getString("row_name"));

					seat.setSeatNumber(rs.getInt("seat_number"));

					seat.setTypeName(rs.getString("type_name"));

					seat.setPrice(rs.getDouble("price"));

					seat.setStatus(rs.getString("status"));

					seats.add(seat);
				}
			}
		}

		return seats;
	}
}