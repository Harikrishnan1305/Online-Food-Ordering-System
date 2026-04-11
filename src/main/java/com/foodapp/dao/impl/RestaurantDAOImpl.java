package com.foodapp.dao.impl;

import com.foodapp.dao.RestaurantDAO;
import com.foodapp.model.Restaurant;
import com.foodapp.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * RestaurantDAOImpl — JDBC implementation of RestaurantDAO.
 */
public class RestaurantDAOImpl implements RestaurantDAO {

    @Override
    public boolean addRestaurant(Restaurant restaurant) {
        String sql = "INSERT INTO restaurants (name, cuisine, rating, delivery_time, image_url, is_active) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, restaurant.getName());
            ps.setString(2, restaurant.getCuisine());
            ps.setDouble(3, restaurant.getRating());
            ps.setInt(4, restaurant.getDeliveryTime());
            ps.setString(5, restaurant.getImageUrl());
            ps.setBoolean(6, restaurant.isActive());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    @Override
    public Restaurant getRestaurantById(int id) {
        String sql = "SELECT * FROM restaurants WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToRestaurant(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return null;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants WHERE is_active = TRUE ORDER BY rating DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                restaurants.add(mapResultSetToRestaurant(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return restaurants;
    }

    @Override
    public List<Restaurant> searchRestaurants(String keyword) {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants WHERE is_active = TRUE AND (name LIKE ? OR cuisine LIKE ?) ORDER BY rating DESC";
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
                restaurants.add(mapResultSetToRestaurant(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return restaurants;
    }

    @Override
    public boolean updateRestaurant(Restaurant restaurant) {
        String sql = "UPDATE restaurants SET name = ?, cuisine = ?, rating = ?, delivery_time = ?, image_url = ?, is_active = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, restaurant.getName());
            ps.setString(2, restaurant.getCuisine());
            ps.setDouble(3, restaurant.getRating());
            ps.setInt(4, restaurant.getDeliveryTime());
            ps.setString(5, restaurant.getImageUrl());
            ps.setBoolean(6, restaurant.isActive());
            ps.setInt(7, restaurant.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    @Override
    public boolean deleteRestaurant(int id) {
        String sql = "DELETE FROM restaurants WHERE id = ?";
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

    // ========== Helper Methods ==========

    private Restaurant mapResultSetToRestaurant(ResultSet rs) throws SQLException {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(rs.getInt("id"));
        restaurant.setName(rs.getString("name"));
        restaurant.setCuisine(rs.getString("cuisine"));
        restaurant.setRating(rs.getDouble("rating"));
        restaurant.setDeliveryTime(rs.getInt("delivery_time"));
        restaurant.setImageUrl(rs.getString("image_url"));
        restaurant.setActive(rs.getBoolean("is_active"));
        return restaurant;
    }

    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        DBConnection.closeConnection(conn);
    }
}
