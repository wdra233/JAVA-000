package com.eric.load;

import com.eric.utils.JDBCUtils;
import com.mysql.cj.jdbc.PreparedStatementWrapper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CSVFileLoader {
    public void loadOrdersFromResource(String fileName) {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "LOAD DATA INFILE ? INTO TABLE t_orders FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 ROWS";
            conn.setAutoCommit(false);
            statement = conn.prepareStatement(sql);
            statement.setObject(1, fileName);
            statement.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                e.printStackTrace();
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            JDBCUtils.resetAutoCommit(conn);
            JDBCUtils.closeResource(conn, statement);
        }
    }
}
