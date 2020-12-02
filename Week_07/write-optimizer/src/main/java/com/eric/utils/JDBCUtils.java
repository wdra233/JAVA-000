package com.eric.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class JDBCUtils {
    private static final String CONFIG_FILE = "/hikaricp.properties";
    private static HikariDataSource hikariDataSource = new HikariDataSource(new HikariConfig(CONFIG_FILE));

    public static Connection getConnection() throws SQLException {
        Connection connection = hikariDataSource.getConnection();
        return connection;
    }

    public static void closeResource(Connection conn, Statement statement) {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        try {
            if(statement != null) statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    public static void resetAutoCommit(Connection conn) {
        if(conn != null) {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }
}
