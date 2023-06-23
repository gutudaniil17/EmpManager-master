package com.example.demo.repository;

import com.example.demo.model.Employee;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepositoryJDBC {
    List<Employee> findAll();
    void save(Employee employee);
    Employee findById(long id);
    void deleteById(long id);
}
