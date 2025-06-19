package dao;

import entity.Customer;
import control.JDBCConnection;

import java.sql.*;
import java.util.*;

public class CustomerDAOImpl implements IBaseDAO<Customer> {
    @Override
    public void add(Customer cus) {
        String sql = "INSERT INTO customer (customerId, name, phone, gender) VALUES (?, ?, ?, ?)";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cus.getCustomerId());
            pstmt.setString(2, cus.getName());
            pstmt.setString(3, cus.getPhone());
            pstmt.setString(4, cus.getGender());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM customer WHERE customerId = ?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Customer cus) {
        String sql = "UPDATE customer SET name=?, phone=?, gender=? WHERE customerId=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cus.getName());
            pstmt.setString(2, cus.getPhone());
            pstmt.setString(3, cus.getGender());
            pstmt.setInt(4, cus.getCustomerId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Customer getById(int id) {
        String sql = "SELECT * FROM customer WHERE customerId=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("customerId"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("gender")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try (Connection conn = JDBCConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Customer(
                        rs.getInt("customerId"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("gender")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}