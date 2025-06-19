package dao;

import entity.Order;
import control.JDBCConnection;

import java.sql.*;
import java.util.*;

public class OrderDAOImpl implements IBaseDAO<Order> {
    @Override
    public void add(Order order) {
        String sql = "INSERT INTO orders (orderId, deskId, customerId, empId, startTime, endTime, totalAmount) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, order.getOrderId());
            pstmt.setInt(2, order.getDeskId());
            pstmt.setInt(3, order.getCustomerId());
            pstmt.setInt(4, order.getEmpId());
            pstmt.setTimestamp(5, new java.sql.Timestamp(order.getStartTime().getTime()));
            if (order.getEndTime() != null) {
                pstmt.setTimestamp(6, new java.sql.Timestamp(order.getEndTime().getTime()));
            } else {
                pstmt.setNull(6, Types.TIMESTAMP);
            }
            pstmt.setDouble(7, order.getTotalAmount());
            pstmt.executeUpdate();

            // 新增：下单后设置餐台为占用
            DeskDAOImpl deskDAO = new DeskDAOImpl();
            deskDAO.updateStatus(order.getDeskId(), "占用");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM orders WHERE orderId = ?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Order order) {
        String sql = "UPDATE orders SET deskId=?, customerId=?, empId=?, startTime=?, endTime=?, totalAmount=? WHERE orderId=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, order.getDeskId());
            pstmt.setInt(2, order.getCustomerId());
            pstmt.setInt(3, order.getEmpId());
            pstmt.setTimestamp(4, new java.sql.Timestamp(order.getStartTime().getTime()));
            if (order.getEndTime() != null) {
                pstmt.setTimestamp(5, new java.sql.Timestamp(order.getEndTime().getTime()));
            } else {
                pstmt.setNull(5, Types.TIMESTAMP);
            }
            pstmt.setDouble(6, order.getTotalAmount());
            pstmt.setInt(7, order.getOrderId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Order getById(int id) {
        String sql = "SELECT * FROM orders WHERE orderId=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                java.util.Date startTime = rs.getTimestamp("startTime");
                java.util.Date endTime = rs.getTimestamp("endTime");
                return new Order(
                        rs.getInt("orderId"),
                        rs.getInt("deskId"),
                        rs.getInt("customerId"),
                        rs.getInt("empId"),
                        startTime,
                        endTime,
                        rs.getDouble("totalAmount")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getAll() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Connection conn = JDBCConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                java.util.Date startTime = rs.getTimestamp("startTime");
                java.util.Date endTime = rs.getTimestamp("endTime");
                list.add(new Order(
                        rs.getInt("orderId"),
                        rs.getInt("deskId"),
                        rs.getInt("customerId"),
                        rs.getInt("empId"),
                        startTime,
                        endTime,
                        rs.getDouble("totalAmount")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}