package com.foodapp.dao.impl;

import com.foodapp.dao.OrderDAO;
import com.foodapp.model.Order;
import com.foodapp.model.OrderItem;
import com.foodapp.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderDAOImpl — JDBC implementation of OrderDAO.
 * Uses transactions for atomic order + order_items insertion.
 * Retrieves auto-generated orderId via Statement.RETURN_GENERATED_KEYS.
 */
public class OrderDAOImpl implements OrderDAO {

    @Override
    public int placeOrder(Order order, List<OrderItem> items) {
        String orderSql = "INSERT INTO orders (user_id, restaurant_id, total_amount, status, payment_mode, address) VALUES (?, ?, ?, ?, ?, ?)";
        String itemSql = "INSERT INTO order_items (order_id, menu_id, quantity, price) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement orderPs = null;
        PreparedStatement itemPs = null;
        ResultSet generatedKeys = null;
        int generatedOrderId = -1;

        try {
            conn = DBConnection.getConnection();

            // ===== BEGIN TRANSACTION =====
            conn.setAutoCommit(false);

            // 1. Insert into orders table
            orderPs = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            orderPs.setInt(1, order.getUserId());
            orderPs.setInt(2, order.getRestaurantId());
            orderPs.setDouble(3, order.getTotalAmount());
            orderPs.setString(4, order.getStatus() != null ? order.getStatus() : "PLACED");
            orderPs.setString(5, order.getPaymentMode());
            orderPs.setString(6, order.getAddress());
            orderPs.executeUpdate();

            // 2. Retrieve the auto-generated orderId
            generatedKeys = orderPs.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedOrderId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve generated order ID");
            }

            // 3. Insert all order items using the generated orderId
            itemPs = conn.prepareStatement(itemSql);
            for (OrderItem item : items) {
                itemPs.setInt(1, generatedOrderId);
                itemPs.setInt(2, item.getMenuId());
                itemPs.setInt(3, item.getQuantity());
                itemPs.setDouble(4, item.getPrice());
                itemPs.addBatch();
            }
            itemPs.executeBatch();

            // ===== COMMIT TRANSACTION =====
            conn.commit();
            return generatedOrderId;

        } catch (SQLException e) {
            // ===== ROLLBACK ON FAILURE =====
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("Transaction rolled back due to error");
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return -1;

        } finally {
            // Reset auto-commit
            if (conn != null) {
                try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
            }
            try { if (generatedKeys != null) generatedKeys.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (itemPs != null) itemPs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (orderPs != null) orderPs.close(); } catch (SQLException e) { e.printStackTrace(); }
            DBConnection.closeConnection(conn);
        }
    }

    @Override
    public Order getOrderById(int orderId) {
        String sql = "SELECT o.*, r.name AS restaurant_name, u.name AS user_name " +
                     "FROM orders o " +
                     "JOIN restaurants r ON o.restaurant_id = r.id " +
                     "JOIN users u ON o.user_id = u.id " +
                     "WHERE o.order_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToOrder(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return null;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.*, r.name AS restaurant_name, u.name AS user_name " +
                     "FROM orders o " +
                     "JOIN restaurants r ON o.restaurant_id = r.id " +
                     "JOIN users u ON o.user_id = u.id " +
                     "WHERE o.user_id = ? ORDER BY o.order_date DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                orders.add(mapResultSetToOrder(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return orders;
    }

    @Override
    public List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT oi.*, m.name AS menu_name FROM order_items oi " +
                     "JOIN menu m ON oi.menu_id = m.id " +
                     "WHERE oi.order_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setOrderItemId(rs.getInt("order_item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setMenuId(rs.getInt("menu_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                item.setMenuName(rs.getString("menu_name"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return items;
    }

    @Override
    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, ps, null);
        }
    }

    // ========== Helper Methods ==========

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setRestaurantId(rs.getInt("restaurant_id"));
        order.setTotalAmount(rs.getDouble("total_amount"));
        order.setStatus(rs.getString("status"));
        order.setPaymentMode(rs.getString("payment_mode"));
        order.setAddress(rs.getString("address"));
        order.setOrderDate(rs.getTimestamp("order_date"));
        order.setRestaurantName(rs.getString("restaurant_name"));
        order.setUserName(rs.getString("user_name"));
        return order;
    }

    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
        DBConnection.closeConnection(conn);
    }
}
