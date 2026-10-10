package com.myfinance.platform.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Function;

public abstract class BaseRepository {

    protected <R> R inTransaction(Function<Connection, R> work) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            
            try {
                R result = work.apply(conn);
                conn.commit();
                return result;
            } catch (Exception ex) {
                try { 
                    conn.rollback(); 
                } catch (SQLException ignored) {}
                throw new RuntimeException("Transaction failed", ex);
            } finally {
                try { 
                    conn.setAutoCommit(true); 
                } catch (SQLException ignored) {}
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Failed to obtain connection", e);
        }
    }

    protected void inTransactionVoid(java.util.function.Consumer<Connection> work) {
        inTransaction(conn -> { 
            work.accept(conn); 
            return null; 
        });
    }
}
