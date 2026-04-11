package com.foodapp.dao.impl;

import com.foodapp.dao.MenuDAO;
import com.foodapp.model.Menu;
import com.foodapp.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MenuDAOImpl — JDBC implementation of MenuDAO.
 */
public class MenuDAOImpl implements MenuDAO {

    @Override
    public boolean addMenu(Menu menu) {
        String sql = "INSERT INTO menu (restaurant_id, name, description, price, rating, image_url, is_available) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, menu.getRestaurantId());
            ps.setString(2, menu.getName());
            ps.setString(3, menu.getDescription());
            ps.setDouble(4, menu.getPrice());
            ps.setDouble(5, menu.getRating());
            ps.setString(6, menu.getImageUrl());
            ps.setBoolean(7, menu.isAvailable());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    @Override
    public Menu getMenuById(int id) {
        String sql = "SELECT m.*, r.name AS restaurant_name FROM menu m " +
                     "JOIN restaurants r ON m.restaurant_id = r.id WHERE m.id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToMenu(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return null;
    }

    @Override
    public List<Menu> getMenuByRestaurantId(int restaurantId) {
        List<Menu> menuList = new ArrayList<>();
        String sql = "SELECT m.*, r.name AS restaurant_name FROM menu m " +
                     "JOIN restaurants r ON m.restaurant_id = r.id " +
                     "WHERE m.restaurant_id = ? AND m.is_available = TRUE ORDER BY m.rating DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, restaurantId);
            rs = ps.executeQuery();

            while (rs.next()) {
                menuList.add(mapResultSetToMenu(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return menuList;
    }

    @Override
    public boolean updateMenu(Menu menu) {
        String sql = "UPDATE menu SET name = ?, description = ?, price = ?, rating = ?, image_url = ?, is_available = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, menu.getName());
            ps.setString(2, menu.getDescription());
            ps.setDouble(3, menu.getPrice());
            ps.setDouble(4, menu.getRating());
            ps.setString(5, menu.getImageUrl());
            ps.setBoolean(6, menu.isAvailable());
            ps.setInt(7, menu.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    @Override
    public boolean deleteMenu(int id) {
        String sql = "DELETE FROM menu WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    @Override
    public List<Menu> searchMenu(String keyword) {
        List<Menu> menuList = new ArrayList<>();
        String sql = "SELECT m.*, r.name AS restaurant_name FROM menu m " +
                     "JOIN restaurants r ON m.restaurant_id = r.id " +
                     "WHERE m.is_available = TRUE AND (m.name LIKE ? OR m.description LIKE ?) ORDER BY m.rating DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            String searchTerm = "%" + keyword + "%";
            ps.setString(1, searchTerm);
            ps.setString(2, searchTerm);
            rs = ps.executeQuery();

            while (rs.next()) {
                menuList.add(mapResultSetToMenu(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return menuList;
    }

    // ========== Helper Methods ==========

    private Menu mapResultSetToMenu(ResultSet rs) throws SQLException {
        Menu menu = new Menu();
        menu.setId(rs.getInt("id"));
        menu.setRestaurantId(rs.getInt("restaurant_id"));
        menu.setName(rs.getString("name"));
        menu.setDescription(rs.getString("description"));
        menu.setPrice(rs.getDouble("price"));
        menu.setRating(rs.getDouble("rating"));
        menu.setImageUrl(rs.getString("image_url"));
        menu.setAvailable(rs.getBoolean("is_available"));
        menu.setRestaurantName(rs.getString("restaurant_name"));
        return menu;
    }

    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        DBConnection.closeConnection(conn);
    }
}
