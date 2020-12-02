package com.eric.batch.dao;

import com.eric.domain.Order;
import com.eric.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void batchInsertOrder() {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "INSERT INTO t_orders (detail) VALUES (?)";
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            for(int i = 1; i <= 1000000; i++) {
                preparedStatement.setObject(1, "order_" + i);
                preparedStatement.addBatch();
                if(i % 5000 == 0) {
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }
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
            JDBCUtils.closeResource(conn, preparedStatement);
        }
    }
}
