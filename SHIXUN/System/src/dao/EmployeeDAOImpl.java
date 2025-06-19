package dao;

import entity.Employee;
import control.JDBCConnection;

import java.sql.*;
import java.util.*;

public class EmployeeDAOImpl implements IBaseDAO<Employee> {
    @Override
    public void add(Employee emp) {
        String sql = "INSERT INTO employee (empId, name, gender, position, address, salary) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, emp.getEmpId());
            pstmt.setString(2, emp.getName());
            pstmt.setString(3, emp.getGender());
            pstmt.setString(4, emp.getPosition());
            pstmt.setString(5, emp.getAddress());
            pstmt.setDouble(6, emp.getSalary());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM employee WHERE empId = ?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Employee emp) {
        String sql = "UPDATE employee SET name=?, gender=?, position=?, address=?, salary=? WHERE empId=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emp.getName());
            pstmt.setString(2, emp.getGender());
            pstmt.setString(3, emp.getPosition());
            pstmt.setString(4, emp.getAddress());
            pstmt.setDouble(5, emp.getSalary());
            pstmt.setInt(6, emp.getEmpId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Employee getById(int id) {
        String sql = "SELECT * FROM employee WHERE empId=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Employee(
                        rs.getInt("empId"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("position"),
                        rs.getString("address"),
                        rs.getDouble("salary")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        try (Connection conn = JDBCConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Employee(
                        rs.getInt("empId"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("position"),
                        rs.getString("address"),
                        rs.getDouble("salary")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}