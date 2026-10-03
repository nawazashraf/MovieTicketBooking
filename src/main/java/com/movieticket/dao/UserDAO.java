
package com.movieticket.dao;

import com.movieticket.model.UserBean;
import com.movieticket.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class UserDAO {

	// Register a new user (with security question + answer)

	public boolean registerUser(UserBean user) {

		String sql = "INSERT INTO users "
				+ "(id, name, email, password, phone, role, status, security_question, security_answer) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, UUID.randomUUID().toString());
			statement.setString(2, user.getName());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getPassword());
			statement.setString(5, user.getPhone());
			statement.setString(6, user.getRole());
			statement.setBoolean(7, true);
			statement.setString(8, user.getSecurityQuestion());
			statement.setString(9, user.getSecurityAnswer());

			return statement.executeUpdate() > 0;

		} catch (SQLException e) {

			e.printStackTrace();

			return false;
		}
	}

	// Login user using email and password

	public UserBean loginUser(String email, String password) {

		String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, email);
			statement.setString(2, password);

			try (ResultSet resultSet = statement.executeQuery()) {

				if (resultSet.next()) {

					return mapResultSet(resultSet);
				}
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

	// Find user using ID

	public UserBean getUserById(String id) {

		String sql = "SELECT * FROM users WHERE id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {

				if (resultSet.next()) {

					return mapResultSet(resultSet);
				}
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

	// Find user using email

	public UserBean getUserByEmail(String email) {

		String sql = "SELECT * FROM users WHERE email = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, email);

			try (ResultSet resultSet = statement.executeQuery()) {

				if (resultSet.next()) {

					return mapResultSet(resultSet);
				}
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

	// Get only the security question for a given email

	public String getSecurityQuestionByEmail(String email) {

		String sql = "SELECT security_question FROM users WHERE email = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, email);

			try (ResultSet resultSet = statement.executeQuery()) {

				if (resultSet.next()) {

					return resultSet.getString("security_question");
				}
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

	// Verify security answer for a given email

	public boolean verifySecurityAnswer(String email, String answer) {

		String sql = "SELECT security_answer FROM users WHERE email = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, email);

			try (ResultSet resultSet = statement.executeQuery()) {

				if (resultSet.next()) {

					String storedAnswer = resultSet.getString("security_answer");

					if (storedAnswer == null || answer == null) {
						return false;
					}

					return storedAnswer.trim().equalsIgnoreCase(answer.trim());
				}
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return false;
	}

	// Update password

	public boolean updatePassword(String userId, String newPassword) {

		String sql = "UPDATE users SET password = ? WHERE id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, newPassword);
			statement.setString(2, userId);

			return statement.executeUpdate() > 0;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	// Update user profile

	public boolean updateProfile(String userId, String name, String phone) {

		String sql = "UPDATE users SET name = ?, phone = ? WHERE id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, name);
			statement.setString(2, phone);
			statement.setString(3, userId);

			return statement.executeUpdate() > 0;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	// Update user profile with password

	public boolean updateProfile(String userId, String name, String phone, String password) {

		String sql = "UPDATE users " + "SET name = ?, phone = ?, password = ? " + "WHERE id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, name);
			statement.setString(2, phone);
			statement.setString(3, password);
			statement.setString(4, userId);

			return statement.executeUpdate() > 0;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	// Update user profile with security question + answer

	public boolean updateProfile(String userId, String name, String phone, String securityQuestion,
			String securityAnswer) {

		String sql = "UPDATE users " + "SET name = ?, phone = ?, security_question = ?, security_answer = ? "
				+ "WHERE id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, name);
			statement.setString(2, phone);
			statement.setString(3, securityQuestion);
			statement.setString(4, securityAnswer);
			statement.setString(5, userId);

			return statement.executeUpdate() > 0;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	// Update user profile with password + security question + answer

	public boolean updateProfile(String userId, String name, String phone, String password, String securityQuestion,
			String securityAnswer) {

		String sql = "UPDATE users " + "SET name = ?, phone = ?, password = ?, "
				+ "security_question = ?, security_answer = ? " + "WHERE id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, name);
			statement.setString(2, phone);
			statement.setString(3, password);
			statement.setString(4, securityQuestion);
			statement.setString(5, securityAnswer);
			statement.setString(6, userId);

			return statement.executeUpdate() > 0;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

	// Activate account

	public boolean activateAccount(String id) {

		String sql = "UPDATE users SET status = 1 WHERE id = ?";

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, id);

			return statement.executeUpdate() > 0;

		} catch (SQLException e) {

			e.printStackTrace();

			return false;
		}
	}

	// ==================== PRIVATE HELPERS ====================

	private UserBean mapResultSet(ResultSet rs) throws SQLException {

		UserBean user = new UserBean();

		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setPhone(rs.getString("phone"));
		user.setRole(rs.getString("role"));
		user.setStatus(rs.getBoolean("status"));

		user.setSecurityQuestion(rs.getString("security_question"));
		user.setSecurityAnswer(rs.getString("security_answer"));

		Timestamp createdAt = rs.getTimestamp("created_at");
		user.setCreatedAt(createdAt);

		return user;
	}
}