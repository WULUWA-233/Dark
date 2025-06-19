package dao;

import entity.Desk;
import control.JDBCConnection;

import java.sql.*;
import java.util.*;

public class DeskDAOImpl implements IBaseDAO<Desk> {
    @Override
    public void add(Desk desk) {
        String sql = "INSERT INTO desk (deskId, areaId, capacity, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, desk.getDeskId());
            pstmt.setInt(2, desk.getAreaId());
            pstmt.setInt(3, desk.getCapacity());
            pstmt.setString(4, desk.getStatus());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM desk WHERE deskId = ?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Desk desk) {
        String sql = "UPDATE desk SET areaId=?, capacity=?, status=? WHERE deskId=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, desk.getAreaId());
            pstmt.setInt(2, desk.getCapacity());
            pstmt.setString(3, desk.getStatus());
            pstmt.setInt(4, desk.getDeskId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Desk getById(int id) {
        String sql = "SELECT * FROM desk WHERE deskId=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Desk(
                        rs.getInt("deskId"),
                        rs.getInt("areaId"),
                        rs.getInt("capacity"),
                        rs.getString("status")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Desk> getAll() {
        List<Desk> list = new ArrayList<>();
        String sql = "SELECT * FROM desk";
        try (Connection conn = JDBCConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Desk(
                        rs.getInt("deskId"),
                        rs.getInt("areaId"),
                        rs.getInt("capacity"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    //通过状态查找所有 Desk
    public List<Desk> getByStatus(String status) {
        List<Desk> list = new ArrayList<>();
        String sql = "SELECT * FROM desk WHERE status=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new Desk(
                        rs.getInt("deskId"),
                        rs.getInt("areaId"),
                        rs.getInt("capacity"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    //更新 Desk 状态
    public void updateStatus(int deskId, String status) {
        String sql = "UPDATE desk SET status=? WHERE deskId=?";
        try (Connection conn = JDBCConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, deskId);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}