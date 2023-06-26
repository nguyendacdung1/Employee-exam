package com.example.basejavaee.connect;

import com.example.basejavaee.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeConnector {
    private String jdbcURL = "jdbc:mysql://localhost:3306/employees?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    private static final String INSERT_EMPLOYEE = "INSERT INTO employees" + "  (full_name, birthday, address, position, department) VALUES " +
            " (?, ?, ?, ?, ?);";
    private static final String SELECT_ALL = "select * from employees";
    private static final String SELECT_EMPLOYEE_ID = "select id,full_name, birthday, address, position, department from employees where id =?";
    private static final String DELETE = "delete from employees where id = ?;";
    private static final String DELETE_ALL = "delete from employees";
    private static final String UPDATE = "update employees set full_name = ?,birthday= ?, address = ?,position= ?, department =? where id = ?;";

    protected synchronized Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            printSQLException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertEmployee(Employee employee) throws SQLException {
        System.out.println(INSERT_EMPLOYEE);
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE)) {
            preparedStatement.setString(1, employee.getFull_name());
            preparedStatement.setString(2, employee.getBirthday());
            preparedStatement.setString(3, employee.getAddress());
            preparedStatement.setString(4, employee.getPosition());
            preparedStatement.setString(5, employee.getDepartment());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Employee selectEmployee(int id) {
        Employee employee = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String full_name = rs.getString("full_name");
                String birthday = rs.getString("birthday");
                String address = rs.getString("address");
                String position = rs.getString("position");
                String department = rs.getString("department");
                employee = new Employee(id, full_name, birthday, address, position, department);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return employee;
    }

    public List<Employee> selectAllEmployees() {
        List <Employee> employees = new ArrayList< >();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);) {
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String full_name = rs.getString("full_name");
                String birthday = rs.getString("birthday");
                String address = rs.getString("address");
                String position = rs.getString("position");
                String department = rs.getString("department");
                employees.add(new Employee(id, full_name, birthday, address, position, department));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return employees;
    }

    public boolean deleteEmployee(int ID) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE);) {
            statement.setInt(1, ID);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean deleteAll() throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_ALL);) {
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateEmployee(Employee employee) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE);) {
            statement.setString(1, employee.getFull_name());
            statement.setString(2, employee.getBirthday());
            statement.setString(3, employee.getAddress());
            statement.setString(4, employee.getPosition());
            statement.setString(5, employee.getDepartment());
            statement.setInt(6, employee.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
