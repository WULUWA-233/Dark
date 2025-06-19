package dao;

import entity.Category;
import control.JDBCConnection;

import java.sql.*;
import java.util.*;

public class CategoryDAOImpl implements IBaseDAO<Category> {
    @Override
    public void add(Category cat) {
        String sql = "INSERT INTO category (categoryId, categoryName, description) VALUES (?, ?, ?)";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cat.getCategoryId());
            pstmt.setString(2, cat.getCategoryName());
            pstmt.setString(3, cat.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM category WHERE categoryId = ?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Category cat) {
        String sql = "UPDATE category SET categoryName=?, description=? WHERE categoryId=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cat.getCategoryName());
            pstmt.setString(2, cat.getDescription());
            pstmt.setInt(3, cat.getCategoryId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Category getById(int id) {
        String sql = "SELECT * FROM category WHERE categoryId=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Category(
                        rs.getInt("categoryId"),
                        rs.getString("categoryName"),
                        rs.getString("description")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Category> getAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM category";
        try (Connection conn = JDBCConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Category(
                        rs.getInt("categoryId"),
                        rs.getString("categoryName"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}