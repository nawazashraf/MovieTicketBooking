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

    // Register a new user
    public boolean registerUser(UserBean user) {

        String sql = "INSERT INTO users "
                   + "(id, name, email, password, phone, role, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, UUID.randomUUID().toString());
            statement.setString(2, user.getName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getPhone());
            statement.setString(6, user.getRole());
            statement.setBoolean(7, true);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Login user using email and password
    public UserBean loginUser(String email, String password) {

        String sql = "SELECT * FROM users "
                   + "WHERE email = ? AND password = ? AND status = TRUE";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                UserBean user = new UserBean();

                user.setId(resultSet.getString("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(resultSet.getString("role"));
                user.setStatus(resultSet.getBoolean("status"));

                Timestamp createdAt = resultSet.getTimestamp("created_at");
                user.setCreatedAt(createdAt);

                return user;
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

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                UserBean user = new UserBean();

                user.setId(resultSet.getString("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(resultSet.getString("role"));
                user.setStatus(resultSet.getBoolean("status"));
                user.setCreatedAt(resultSet.getTimestamp("created_at"));

                return user;
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

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                UserBean user = new UserBean();

                user.setId(resultSet.getString("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(resultSet.getString("role"));
                user.setStatus(resultSet.getBoolean("status"));
                user.setCreatedAt(resultSet.getTimestamp("created_at"));

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    // Update password
    public boolean changePassword(String id, String currentPassword, String newPassword) {

        String sql = "UPDATE users SET password = ? "
                   + "WHERE id = ? AND password = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newPassword);
            statement.setString(2, id);
            statement.setString(3, currentPassword);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Update user profile
    public boolean updateProfile(String id, String name, String email, String phone) {

        String sql = "UPDATE users "
                   + "SET name = ?, email = ?, phone = ? "
                   + "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setString(4, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}