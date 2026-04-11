package com.foodapp.dao;

import com.foodapp.model.Order;
import com.foodapp.model.OrderItem;
import java.util.List;

/**
 * OrderDAO — Data Access Object interface for Order and OrderItem entities.
 */
public interface OrderDAO {

    /**
     * Place a new order with all its items in a single transaction.
     * Returns the auto-generated orderId, or -1 on failure.
     */
    int placeOrder(Order order, List<OrderItem> items);

    /**
     * Get an order by its primary key.
     */
    Order getOrderById(int orderId);

    /**
     * Get all orders placed by a specific user.
     */
    List<Order> getOrdersByUserId(int userId);

    /**
     * Get all items in a specific order.
     */
    List<OrderItem> getOrderItems(int orderId);

    /**
     * Update the status of an order.
     */
    boolean updateOrderStatus(int orderId, String status);
}
