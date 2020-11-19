package com.eric.preparestastatement;

import com.eric.model.Employee;
import com.eric.preparestastatement.dao.EmployeeDaoImpl;
import com.eric.util.JDBCUtils;

import java.sql.Connection;

public class PrepareStatementTest {
    public static void main(String[] args) {
        try {
            EmployeeDaoImpl dao = new EmployeeDaoImpl();
            long start = System.currentTimeMillis();
            dao.batchInsertEmployee();
            long duration = System.currentTimeMillis() - start;
            System.out.println("Duration time of batch insert 20000 times is " + duration / 1000.0 + "s");
            // duration time using connection is 3.257s
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
