package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepositoryJDBCImpl implements EmployeeRepositoryJDBC {
    private final String dbURL = "jdbc:mysql://localhost:3306/mydb";
    private final String username = "root";
    private final String password = "root";


    @Override
    public List<Employee> findAll() {
        List<Employee> result = new ArrayList<>();
        String query = "select * from employees";
        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                Employee employee = new Employee(id, firstName, lastName, email);
                result.add(employee);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public void save(Employee employee) {

        long id = employee.getId();
        String first = employee.getFirstName();
        String last = employee.getLastName();
        String email = employee.getEmail();
        String query;
        if (employee.getId() == 0) {
            query = "insert into employees (first_name,last_name,email) " +
                    "values (' " + first + " ' , ' " + last + " ' , ' " + email + "')";
        } else {
            query = "update employees " +
                    "set first_name = ' " + first + " ', last_name = ' " + last + "' , email = ' " + email + " '" +
                    " where id = " + id;
        }
        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
            conn.prepareStatement(query).executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }

    @Override
    public Employee findById(long id) {
        Employee result = new Employee();
        String query = "select * from employees where id = " + id;
        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id1 = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                result = new Employee(id1, firstName, lastName, email);

            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public void deleteById(long id) {
        String query = "delete from employees where id = " + id;
        try(Connection conn = DriverManager.getConnection(dbURL,username,password)) {
            conn.prepareStatement(query).executeUpdate();        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }
}
