package com.eric.nativejdbc;

import com.eric.util.JDBCUtils;
import com.eric.nativejdbc.dao.EmployeeDaoImpl;
import com.eric.model.Employee;

import java.sql.Connection;
import java.util.List;

public class JDBCConnectionTest {
    public static void main(String[] args) {
        //
        try {
            EmployeeDaoImpl dao = new EmployeeDaoImpl();
            List<Employee> employees = dao.getAllEmployees();
            for (Employee employee : employees) {
                System.out.println(employee.toString());
            }
            // Employee(id=1, lastName=John, gender=m, email=john@gmail.com)
            //Employee(id=2, lastName=Marry, gender=w, email=marry@gmail.com)
            //Employee(id=3, lastName=Allen, gender=m, email=allen@gmail.com)
            //Employee(id=4, lastName=Jack, gender=m, email=jack@gmail.com)
            //Employee(id=5, lastName=Eric, gender=m, email=eric@gmail.com)
            //Employee(id=6, lastName=Jenny, gender=w, email=Jenny@gmail.com)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
