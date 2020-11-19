package com.eric.nativejdbc.dao;

import com.eric.model.Employee;
import com.eric.util.JDBCUtils;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

    public List<Employee> getAllEmployees() {
        String sql = "SELECT id, last_name, gender, email FROM employee";
        List<Employee> employees = new ArrayList<>();
        Statement statement = null;
        Connection conn = null;
        ResultSet resultSet = null;
        try {
            conn = JDBCUtils.getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String lastName = resultSet.getString("last_name");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email");
                Employee employee = new Employee(id, lastName, gender, email);
                employees.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, statement, resultSet);
        }
        return employees;
    }
}
