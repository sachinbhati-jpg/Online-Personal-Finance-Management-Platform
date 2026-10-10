package com.myfinance.platform.jdbc;

import com.myfinance.platform.model.Expense;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExpenseRepository extends BaseRepository {

    public Expense insert(Expense expense) {
        return inTransaction(conn -> {
            String sql = "INSERT INTO expense (user_id, expense_date, category, amount, description, created_at) VALUES (?,?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, expense.getUserId());
                ps.setDate(2, Date.valueOf(expense.getDate()));
                ps.setString(3, expense.getCategory());
                ps.setString(4, expense.getAmount().toString());
                ps.setString(5, expense.getDescription());
                ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        Expense e = new Expense();
                        e.setId(rs.getLong(1));
                        e.setUserId(expense.getUserId());
                        e.setDate(expense.getDate());
                        e.setCategory(expense.getCategory());
                        e.setAmount(expense.getAmount());
                        e.setDescription(expense.getDescription());
                        e.setCreatedAt(LocalDateTime.now());
                        return e;
                    }
                    throw new RuntimeException("Failed to retrieve generated ID");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<Expense> findByUserId(Long userId) {
        return inTransaction(conn -> {
            List<Expense> list = new ArrayList<>();
            String sql = "SELECT id, user_id, expense_date, category, amount, description, created_at FROM expense WHERE user_id = ? ORDER BY expense_date DESC";
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

    public List<Expense> findAllForAdmin() {
        return inTransaction(conn -> {
            List<Expense> list = new ArrayList<>();
            String sql = "SELECT id, user_id, expense_date, category, amount, description, created_at FROM expense ORDER BY expense_date DESC";
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

    public Optional<Expense> findById(Long id, Long userId) {
        return inTransaction(conn -> {
            String sql = "SELECT id, user_id, expense_date, category, amount, description, created_at FROM expense WHERE id = ? AND user_id = ?";
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

    public void update(Expense expense, Long userId) {
        inTransactionVoid(conn -> {
            String sql = "UPDATE expense SET expense_date = ?, category = ?, amount = ?, description = ? WHERE id = ? AND user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setDate(1, Date.valueOf(expense.getDate()));
                ps.setString(2, expense.getCategory());
                ps.setBigDecimal(3, expense.getAmount());
                ps.setString(4, expense.getDescription());
                ps.setLong(5, expense.getId());
                ps.setLong(6, userId);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteById(Long id, Long userId) {
        inTransactionVoid(conn -> {
            String sql = "DELETE FROM expense WHERE id = ? AND user_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setLong(2, userId);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Expense mapRow(ResultSet rs) throws SQLException {
        return new Expense(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getDate("expense_date").toLocalDate(),
                rs.getString("category"),
                new BigDecimal(rs.getString("amount")),
                rs.getString("description"),
                rs.getTimestamp("created_at").toLocalDateTime());
    }
}