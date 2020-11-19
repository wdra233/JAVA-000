package com.eric.nativejdbc.dao;

import com.eric.model.Employee;

import java.sql.Connection;
import java.util.List;

public interface EmployeeDao {
    List<Employee> getAllEmployees();
}
