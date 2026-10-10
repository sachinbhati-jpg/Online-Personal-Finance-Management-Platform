package com.myfinance.platform.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionFactory {
    private static final String URL = setting("FINANCE_DB_URL",
            "jdbc:mysql://localhost:3306/finance_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
    private static final String USER = setting("FINANCE_DB_USER", "finance_user");
    private static final String PASS = setting("FINANCE_DB_PASSWORD", "");

    private static final DataSource POOL;

    static {
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(URL);
        cfg.setDriverClassName("com.mysql.cj.jdbc.Driver");
        cfg.setUsername(USER);
        cfg.setPassword(PASS);
        cfg.setMaximumPoolSize(10);
        cfg.setConnectionTimeout(3000);
        cfg.setIdleTimeout(600000);
        cfg.setPoolName("FinancePool");
        POOL = new HikariDataSource(cfg);
    }

    private ConnectionFactory() {
    }

    private static String setting(String name, String defaultValue) {
        return System.getenv().getOrDefault(name, defaultValue);
    }

    public static Connection getConnection() throws SQLException {
        return POOL.getConnection();
    }

    public static void shutdown() {
        if (POOL instanceof HikariDataSource) {
            ((HikariDataSource) POOL).close();
        }
    }
}