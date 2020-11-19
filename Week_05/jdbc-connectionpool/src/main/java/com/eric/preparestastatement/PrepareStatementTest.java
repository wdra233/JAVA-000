package com.eric.preparestastatement;

import com.eric.model.Employee;
import com.eric.preparestastatement.dao.EmployeeDaoImpl;
import com.eric.util.JDBCUtils;

import java.sql.Connection;

public class PrepareStatementTest {
    public static void main(String[] args) {
        try {
            EmployeeDaoImpl dao = new EmployeeDaoImpl();
            Employee employee = dao.getEmployeeById(1);
            System.out.println(employee);
            // Employee(id=1, lastName=John, gender=m, email=john@gmail.com)

            // Test add new employee to DB
//            Employee newEmployee = new Employee(14, "Bruce", "m", "bruce@gmail.com");
//            boolean result = dao.addEmployee(newEmployee);
//            boolean result = dao.updateEmailById("batman@gmail.com", -1);
            // Test transaction
            boolean result = dao.updateTransaction("superman@gmail.com", 13, 12);

            System.out.println(result ? "success!" : "fail");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
