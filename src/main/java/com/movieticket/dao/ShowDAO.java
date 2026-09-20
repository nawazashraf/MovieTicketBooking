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

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
				SELECT
				    sh.*,
				    m.title AS movie_name,
				    ma.name AS mall_name,
				    COUNT(CASE WHEN ss.status = 'AVAILABLE' THEN 1 END) AS available_seats
				FROM shows sh
				JOIN movies m
				    ON sh.movie_id = m.id
				JOIN malls ma
				    ON sh.mall_id = ma.id
				LEFT JOIN show_seats ss
				    ON ss.show_id = sh.id
				GROUP BY sh.id
				ORDER BY sh.show_date, sh.start_time
				""";

		List<ShowBean> shows = new ArrayList<>();

		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {

				ShowBean show = mapShow(rs);

				show.setAvailableSeats(rs.getInt("available_seats"));

				shows.add(show);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return shows;
	}

	public List<ShowBean> getShowsByMovieId(String movieId) {

		String sql = """
				SELECT
				    sh.*,
				    m.title AS movie_name,
				    ma.name AS mall_name,
				    COUNT(CASE WHEN ss.status = 'AVAILABLE' THEN 1 END) AS available_seats
				FROM shows sh
				JOIN movies m
				    ON sh.movie_id = m.id
				JOIN malls ma
				    ON sh.mall_id = ma.id
				LEFT JOIN show_seats ss
				    ON ss.show_id = sh.id
				WHERE sh.movie_id = ?
				GROUP BY sh.id
				ORDER BY sh.show_date, ma.name, sh.start_time
				""";

		List<ShowBean> shows = new ArrayList<>();

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, movieId);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {

					ShowBean show = mapShow(rs);

					show.setAvailableSeats(rs.getInt("available_seats"));

					shows.add(show);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return shows;
	}

	public int getAvailableSeatCount(String showId) {
		String sql = """
				SELECT COUNT(*)
				FROM show_seats
				WHERE show_id = ?
				AND status = 'AVAILABLE'
				""";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, showId);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					return rs.getInt(1);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
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

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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

	private void setInsertValues(PreparedStatement ps, ShowBean show) throws Exception {

		ps.setString(1, show.getShowId());
		ps.setString(2, show.getMovieId());
		ps.setString(3, show.getMallId());
		ps.setString(4, show.getShowDate());
		ps.setString(5, show.getStartTime());
		ps.setString(6, show.getEndTime());
		ps.setString(7, show.getStatus());
	}

	public boolean syncShowSeats(String showId, String mallId, double price) {
		String insertSql = "INSERT IGNORE INTO show_seats (id, show_id, seat_id, price, status) "
				+ "SELECT UUID(), ?, id, ?, 'AVAILABLE' FROM seats WHERE mall_id = ? AND status = TRUE";
		String updateSql = "UPDATE show_seats SET price=? WHERE show_id=? AND status <> 'BOOKED'";
		try (Connection conn = DBConnection.getConnection()) {
			conn.setAutoCommit(false);
			try (PreparedStatement p = conn.prepareStatement(insertSql);
					PreparedStatement u = conn.prepareStatement(updateSql)) {
				p.setString(1, showId);
				p.setDouble(2, price);
				p.setString(3, mallId);
				p.executeUpdate();
				u.setDouble(1, price);
				u.setString(2, showId);
				u.executeUpdate();
				conn.commit();
				return true;
			} catch (Exception e) {
				conn.rollback();
				throw e;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}