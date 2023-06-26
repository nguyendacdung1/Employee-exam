package com.example.basejavaee.WebServlet;

import com.example.basejavaee.connect.EmployeeConnector;
import com.example.basejavaee.entities.Employee;
import com.example.basejavaee.entities.dtos.EmployeeDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EmployeeConnector employeeConnector;

    public void init() {
        employeeConnector = new EmployeeConnector();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertEmployee(request, response);
                    break;
                case "/delete":
                    deleteEmployee(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateEmployee(request, response);
                    break;
                case "/list":
                    listEmployee(request, response);
                    break;
                case "/delete-all":
                    deleteAll(response);
                    break;
                default:
                    listEmployee(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listEmployee(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Employee> employeeList = employeeConnector.selectAllEmployees();
        request.setAttribute("employeeList", employeeList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list-employees.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("employee-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int ID = Integer.parseInt(request.getParameter("ID"));
        Employee employee = employeeConnector.selectEmployee(ID);
        RequestDispatcher dispatcher = request.getRequestDispatcher("employee-form.jsp");
        request.setAttribute("employee", employee);
        dispatcher.forward(request, response);

    }

    private void insertEmployee(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String FullName = request.getParameter("FullName");
        String Birthday = request.getParameter("Birthday");
        String Address = request.getParameter("Address");
        String Position = request.getParameter("Position");
        String Department = request.getParameter("Department");
        EmployeeDto employeeDto = new EmployeeDto(FullName, Birthday, Address, Position, Department);
        Employee newEmployee = new Employee();
        newEmployee.FullName = employeeDto.FullName;
        newEmployee.Birthday = employeeDto.Birthday;
        newEmployee.Address = employeeDto.Address;
        newEmployee.Position = employeeDto.Position;
        newEmployee.Department = employeeDto.Department;
        employeeConnector.insertEmployee(newEmployee);
        response.sendRedirect("list");
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int ID = Integer.parseInt(request.getParameter("ID"));
        String FullName = request.getParameter("FullName");
        String Birthday = request.getParameter("Birthday");
        String Address = request.getParameter("Address");
        String Position = request.getParameter("Position");
        String Department = request.getParameter("Department");

        Employee book = new Employee(ID, FullName, Birthday, Address, Position, Department);
        employeeConnector.updateEmployee(book);
        response.sendRedirect("list");
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int ID = Integer.parseInt(request.getParameter("ID"));
        employeeConnector.deleteEmployee(ID);
        response.sendRedirect("list");

    }

    private void deleteAll(HttpServletResponse response)
            throws SQLException, IOException {
        employeeConnector.deleteAll();
        response.sendRedirect("list");

    }
}
