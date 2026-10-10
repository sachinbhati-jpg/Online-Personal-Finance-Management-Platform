package com.myfinance.platform.jdbc;

import com.myfinance.platform.model.Goal;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GoalRepository extends BaseRepository {

    public Goal insert(Goal goal) {
        return inTransaction(conn -> {
            String sql = "INSERT INTO goal (user_id, name, category, target_amount, current_amount, deadline, created_at) VALUES (?,?,?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, goal.getUserId());
                ps.setString(2, goal.getName());
                ps.setString(3, goal.getCategory());
                ps.setBigDecimal(4, goal.getTargetAmount());
                ps.setBigDecimal(5, goal.getCurrentAmount());
                ps.setDate(6, Date.valueOf(goal.getDeadline()));
                ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        Goal g = new Goal();
                        g.setId(rs.getLong(1));
                        g.setUserId(goal.getUserId());
                        g.setName(goal.getName());
                        g.setCategory(goal.getCategory());
                        g.setTargetAmount(goal.getTargetAmount());
                        g.setCurrentAmount(goal.getCurrentAmount());
                        g.setDeadline(goal.getDeadline());
                        g.setCreatedAt(LocalDateTime.now());
                        return g;
                    }
                    throw new RuntimeException("Failed to retrieve generated ID");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<Goal> findByUserId(Long userId) {
        return inTransaction(conn -> {
            List<Goal> list = new ArrayList<>();
            String sql = "SELECT id, user_id, name, category, target_amount, current_amount, deadline, created_at FROM goal WHERE user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        list.add(mapRow(rs));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return list;
        });
    }

    public List<Goal> findAllForAdmin() {
        return inTransaction(conn -> {
            List<Goal> list = new ArrayList<>();
            String sql = "SELECT id, user_id, name, category, target_amount, current_amount, deadline, created_at FROM goal ORDER BY user_id, deadline";
            try (PreparedStatement ps = conn.prepareStatement(sql);
                    ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return list;
        });
    }

    public Optional<Goal> findById(Long id, Long userId) {
        return inTransaction(conn -> {
            String sql = "SELECT id, user_id, name, category, target_amount, current_amount, deadline, created_at FROM goal WHERE id = ? AND user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setLong(2, userId);
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

    public void updateCurrentAmount(Long id, Long userId, BigDecimal currentAmount) {
        inTransactionVoid(conn -> {
            String sql = "UPDATE goal SET current_amount = ? WHERE id = ? AND user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setBigDecimal(1, currentAmount);
                ps.setLong(2, id);
                ps.setLong(3, userId);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteById(Long id, Long userId) {
        inTransactionVoid(conn -> {
            String sql = "DELETE FROM goal WHERE id = ? AND user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setLong(2, userId);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Goal mapRow(ResultSet rs) throws SQLException {
        return new Goal(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("name"),
                rs.getString("category"),
                rs.getBigDecimal("target_amount"),
                rs.getBigDecimal("current_amount"),
                rs.getDate("deadline").toLocalDate(),
                rs.getTimestamp("created_at").toLocalDateTime());
    }
}