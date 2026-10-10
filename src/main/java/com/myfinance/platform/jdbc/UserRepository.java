package com.myfinance.platform.jdbc;

import com.myfinance.platform.model.User;
import com.myfinance.platform.util.PasswordUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository extends BaseRepository {

    public User register(String username, String email, String rawPassword, String role) {
        return inTransaction(conn -> {
            String sql = "INSERT INTO app_user (username, email, password_hash, role, created_at) VALUES (?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                LocalDateTime now = LocalDateTime.now();
                ps.setString(1, username);
                ps.setString(2, email);
                ps.setString(3, PasswordUtil.hash(rawPassword));
                ps.setString(4, role);
                ps.setTimestamp(5, Timestamp.valueOf(now));
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        User u = new User();
                        u.setId(rs.getLong(1));
                        u.setUsername(username);
                        u.setEmail(email);
                        u.setRole(role);
                        u.setCreatedAt(now);
                        return u;
                    }
                    throw new RuntimeException("Failed to retrieve generated ID");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Optional<User> findByUsername(String username) {
        return inTransaction(conn -> {
            String sql = "SELECT id, username, email, password_hash, role, created_at FROM app_user WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Optional<User> findByEmail(String email) {
        return inTransaction(conn -> {
            String sql = "SELECT id, username, email, password_hash, role, created_at FROM app_user WHERE email = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<User> findAllForAdmin() {
        return inTransaction(conn -> {
            List<User> users = new ArrayList<>();
            String sql = "SELECT id, username, email, role, created_at FROM app_user ORDER BY username";
            try (PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    if (createdAt != null) {
                        user.setCreatedAt(createdAt.toLocalDateTime());
                    }
                    users.add(user);
                }
                return users;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updatePasswordHash(Long userId, String passwordHash) {
        inTransactionVoid(conn -> {
            String sql = "UPDATE app_user SET password_hash = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, passwordHash);
                ps.setLong(2, userId);
                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("Account was not found.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setRole(rs.getString("role"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null)
            u.setCreatedAt(ts.toLocalDateTime());
        return u;
    }
}