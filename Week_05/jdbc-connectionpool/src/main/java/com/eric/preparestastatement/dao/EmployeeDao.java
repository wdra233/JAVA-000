package com.eric.preparestastatement.dao;

import com.eric.model.Employee;

public interface EmployeeDao {
    Employee getEmployeeById(int id);

    boolean updateEmailById(String email, int id);

    boolean deleteEmployeeById(int id);

    boolean addEmployee(Employee employee);

    boolean updateTransaction(String email, int id1, int id2);

    void batchInsertEmployee();
}
