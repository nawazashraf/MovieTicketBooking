package com.movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.movieticket.model.ShowBean;
import com.movieticket.model.SeatBean;
import com.movieticket.util.DBConnection;

public class SeatSelectionDAO {

	// Get the movie and showtime details for the page.
	public ShowBean getShowDetails(String showId) throws SQLException {
		String sql = """
				SELECT sh.id, m.title, ma.name, sh.show_date, sh.start_time
				FROM shows sh
				JOIN movies m ON sh.movie_id = m.id
				JOIN malls ma ON sh.mall_id = ma.id
				WHERE sh.id = ?
				""";
		try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, showId);
			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) {
					return null;
				}

				ShowBean show = new ShowBean();
				show.setShowId(rs.getString("id"));
				show.setMovieName(rs.getString("title"));
				show.setMallName(rs.getString("name"));
				show.setShowDate(rs.getString("show_date"));
				show.setStartTime(rs.getString("start_time"));
				return show;
			}
		}
	}

	// Get the show's seats in row and seat-number order.
	public ArrayList<SeatBean> getSeatsByShowId(String showId) throws SQLException {
		ArrayList<SeatBean> seats = new ArrayList<>();
		String sql = """
				SELECT ss.id, s.row_name, s.seat_number, s.section, st.type_name, ss.price, ss.status
				FROM show_seats ss
				JOIN seats s ON ss.seat_id = s.id
				JOIN seat_types st ON s.seat_type_id = st.id
				WHERE ss.show_id = ?
				ORDER BY s.row_name, s.seat_number
				""";
		try (Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, showId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					seats.add(mapSeat(rs));
				}
			}
		}
		return seats;
	}

	// Fill a seat bean from the current row.
	private SeatBean mapSeat(ResultSet rs) throws SQLException {
		SeatBean seat = new SeatBean();
		seat.setShowSeatId(rs.getString("id"));
		seat.setRowName(rs.getString("row_name"));
		seat.setSeatNumber(rs.getInt("seat_number"));
		seat.setSection(rs.getString("section"));
		seat.setTypeName(rs.getString("type_name"));
		seat.setPrice(rs.getDouble("price"));
		seat.setStatus(rs.getString("status"));
		return seat;
	}
}