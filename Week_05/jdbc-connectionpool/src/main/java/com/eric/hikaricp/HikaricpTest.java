package com.eric.hikaricp;

import com.eric.hikaricp.dao.EmployeeDaoImpl;

public class HikaricpTest {
    public static void main(String[] args) {
        try {
            EmployeeDaoImpl dao = new EmployeeDaoImpl();
//            Employee employee = dao.getEmployeeById(1);
//            System.out.println(employee);
            // Employee(id=1, lastName=John, gender=m, email=john@gmail.com)

            // Test add new employee to DB
//            Employee newEmployee = new Employee(14, "Bruce", "m", "bruce@gmail.com");
//            boolean result = dao.addEmployee(newEmployee);
//            boolean result = dao.updateEmailById("batman@gmail.com", -1);
            // Test transaction
//            boolean result = dao.updateTransaction("superman@gmail.com", 13, 12);
//            System.out.println(result ? "success!" : "fail");

            long start = System.currentTimeMillis();
            dao.batchInsertEmployee();
            long duration = System.currentTimeMillis() - start;
            System.out.println("Duration time of batch insert 20000 times using HikariCP is " + duration / 1000.0 + "s");
            //duration time 1.988s
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
