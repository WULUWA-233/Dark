package dao;

import entity.Dish;
import control.JDBCConnection;

import java.sql.*;
import java.util.*;

public class DishDAOImpl implements IBaseDAO<Dish> {
    @Override
    public void add(Dish dish) {
        String sql = "INSERT INTO dish (dishId, dishName, price, categoryId) VALUES (?, ?, ?, ?)";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dish.getDishId());
            pstmt.setString(2, dish.getDishName());
            pstmt.setDouble(3, dish.getPrice());
            pstmt.setInt(4, dish.getCategoryId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM dish WHERE dishId = ?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Dish dish) {
        String sql = "UPDATE dish SET dishName=?, price=?, categoryId=? WHERE dishId=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dish.getDishName());
            pstmt.setDouble(2, dish.getPrice());
            pstmt.setInt(3, dish.getCategoryId());
            pstmt.setInt(4, dish.getDishId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Dish getById(int id) {
        String sql = "SELECT * FROM dish WHERE dishId=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Dish(
                        rs.getInt("dishId"),
                        rs.getString("dishName"),
                        rs.getDouble("price"),
                        rs.getInt("categoryId")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Dish> getAll() {
        List<Dish> list = new ArrayList<>();
        String sql = "SELECT * FROM dish";
        try (Connection conn = JDBCConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Dish(
                        rs.getInt("dishId"),
                        rs.getString("dishName"),
                        rs.getDouble("price"),
                        rs.getInt("categoryId")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}