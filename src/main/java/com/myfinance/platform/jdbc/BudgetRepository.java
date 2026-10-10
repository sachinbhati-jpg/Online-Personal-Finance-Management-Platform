package com.myfinance.platform.jdbc;

import com.myfinance.platform.model.Budget;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BudgetRepository extends BaseRepository {

    public Budget insert(Budget budget) {
        return inTransaction(conn -> {
            String sql = "INSERT INTO budget (user_id, category, period, limit_amount, spent_amount, created_at) VALUES (?,?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, budget.getUserId());
                ps.setString(2, budget.getCategory());
                ps.setString(3, budget.getPeriod());
                ps.setString(4, budget.getLimitAmount().toString());
                ps.setString(5, budget.getSpentAmount().toString());
                ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        Budget b = new Budget();
                        b.setId(rs.getLong(1));
                        b.setUserId(budget.getUserId());
                        b.setCategory(budget.getCategory());
                        b.setPeriod(budget.getPeriod());
                        b.setLimitAmount(budget.getLimitAmount());
                        b.setSpentAmount(budget.getSpentAmount());
                        b.setCreatedAt(LocalDateTime.now());
                        return b;
                    }
                    throw new RuntimeException("Failed to retrieve generated ID");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<Budget> findByUserId(Long userId) {
        return inTransaction(conn -> {
            List<Budget> list = new ArrayList<>();
            String sql = "SELECT id, user_id, category, period, limit_amount, spent_amount, created_at FROM budget WHERE user_id = ?";
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

    public List<Budget> findAllForAdmin() {
        return inTransaction(conn -> {
            List<Budget> list = new ArrayList<>();
            String sql = "SELECT id, user_id, category, period, limit_amount, spent_amount, created_at FROM budget ORDER BY user_id, category";
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

    public Optional<Budget> findById(Long id, Long userId) {
        return inTransaction(conn -> {
            String sql = "SELECT id, user_id, category, period, limit_amount, spent_amount, created_at FROM budget WHERE id = ? AND user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setLong(2, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void updateSpentAmount(Long budgetId, Long userId, BigDecimal spentAmount) {
        inTransactionVoid(conn -> {
            String sql = "UPDATE budget SET spent_amount = ? WHERE id = ? AND user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, spentAmount.toString());
                ps.setLong(2, budgetId);
                ps.setLong(3, userId);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteById(Long id, Long userId) {
        inTransactionVoid(conn -> {
            String sql = "DELETE FROM budget WHERE id = ? AND user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setLong(2, userId);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Budget mapRow(ResultSet rs) throws SQLException {
        return new Budget(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("category"),
                rs.getString("period"),
                new BigDecimal(rs.getString("limit_amount")),
                new BigDecimal(rs.getString("spent_amount")),
                rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
    }
}
