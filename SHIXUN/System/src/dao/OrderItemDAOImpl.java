package dao;

import entity.OrderItem;
import control.JDBCConnection;

import java.sql.*;
import java.util.*;

public class OrderItemDAOImpl implements IBaseDAO<OrderItem> {
    @Override
    public void add(OrderItem item) {
        String sql = "INSERT INTO order_item (orderItemId, orderId, dishId, dishPrice, totalAmount) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, item.getOrderItemId());
            pstmt.setInt(2, item.getOrderId());
            pstmt.setInt(3, item.getDishId());
            pstmt.setDouble(4, item.getDishPrice());
            pstmt.setDouble(5, item.getTotalAmount());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM order_item WHERE orderItemId = ?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(OrderItem item) {
        String sql = "UPDATE order_item SET orderId=?, dishId=?, dishPrice=?, totalAmount=? WHERE orderItemId=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, item.getOrderId());
            pstmt.setInt(2, item.getDishId());
            pstmt.setDouble(3, item.getDishPrice());
            pstmt.setDouble(4, item.getTotalAmount());
            pstmt.setInt(5, item.getOrderItemId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public OrderItem getById(int id) {
        String sql = "SELECT * FROM order_item WHERE orderItemId=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new OrderItem(
                        rs.getInt("orderItemId"),
                        rs.getInt("orderId"),
                        rs.getInt("dishId"),
                        rs.getDouble("dishPrice"),
                        rs.getDouble("totalAmount")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrderItem> getAll() {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM order_item";
        try (Connection conn = JDBCConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new OrderItem(
                        rs.getInt("orderItemId"),
                        rs.getInt("orderId"),
                        rs.getInt("dishId"),
                        rs.getDouble("dishPrice"),
                        rs.getDouble("totalAmount")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}