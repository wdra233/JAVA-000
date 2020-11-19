package com.eric.preparestastatement.dao;

import com.eric.model.Employee;
import com.eric.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public Employee getEmployeeById(int id) {
        Connection connection = null;
        String sql = "SELECT id, last_name, gender, email FROM employee WHERE id = ?";
        PreparedStatement statement = null;
        Employee employee = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int employeeId = resultSet.getInt("id");
                String lastName = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email");
                employee = new Employee(employeeId, lastName, gender, email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, statement, resultSet);
        }
        return employee;
    }

    @Override
    public boolean updateEmailById(String email, int id) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE employee SET email = ? WHERE id = ?";
        int resultCnts = 0;
        try {
            conn = JDBCUtils.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            resultCnts = update(preparedStatement, email, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, preparedStatement);
        }
        return resultCnts > 0 ? true : false;
    }

    @Override
    public boolean deleteEmployeeById(int id) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM employee WHERE id = ?";
        int resultCnts = 0;
        try {
            conn = JDBCUtils.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            resultCnts = update(preparedStatement, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, preparedStatement);
        }
        return resultCnts > 0 ? true : false;
    }

    @Override
    public boolean addEmployee(Employee employee) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO employee VALUES (?, ?, ?, ?)";
        int resultCnts = 0;
        try {
            conn = JDBCUtils.getConnection();
            preparedStatement = conn.prepareStatement(sql);
            resultCnts = update(preparedStatement, employee.getId(), employee.getLastName(), employee.getGender(), employee.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, preparedStatement);
        }
        return resultCnts > 0 ? true : false;
    }

    @Override
    // transaction
    public boolean updateTransaction(String email, int id1, int id2) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE employee SET email = ? WHERE id = ?";
        int resultCnts = 0;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            int first = update(preparedStatement, email, id1);
            int second = update(preparedStatement, email, id2);
            conn.commit();
            resultCnts = first + second;
        } catch (Exception e) {
            try {
                e.printStackTrace();
                conn.rollback();
                resultCnts = 0;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            JDBCUtils.closeResource(conn, preparedStatement);
        }
        return resultCnts > 0 ? true : false;

    }

    @Override
    public void batchInsertEmployee() {
        // batch insert employee to db 20000 times
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO employee (last_name, gender, email) VALUES(?, ?, ?)";
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(sql);
            for(int i = 1; i <= 20000; i++) {
                preparedStatement.setObject(1, "eric_"+i);
                preparedStatement.setObject(2, "m");
                preparedStatement.setObject(3, "eric_"+i+"@gmail.com");
                preparedStatement.addBatch();
                if(i % 500 == 0) {
                    // execute batch
                    preparedStatement.executeBatch();
                    // clear batch
                    preparedStatement.clearBatch();
                }
            }
            conn.commit();

        } catch (Exception e) {
            try {
                e.printStackTrace();
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            JDBCUtils.closeResource(conn, preparedStatement);
        }
    }

    private int update(PreparedStatement preparedStatement, Object... args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
        return preparedStatement.executeUpdate();
    }
}
