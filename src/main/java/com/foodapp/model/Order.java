package com.foodapp.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Order POJO — Maps to the 'orders' table.
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    private int orderId;
    private int userId;
    private int restaurantId;
    private double totalAmount;
    private String status;
    private String paymentMode;
    private String address;
    private Timestamp orderDate;

    // Transient display fields
    private String restaurantName;
    private String userName;

    // No-arg constructor
    public Order() {}

    // Parameterized constructor (for placing a new order)
    public Order(int userId, int restaurantId, double totalAmount, String status, String paymentMode, String address) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.paymentMode = paymentMode;
        this.address = address;
    }

    // Full constructor
    public Order(int orderId, int userId, int restaurantId, double totalAmount, String status, String paymentMode, String address, Timestamp orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.paymentMode = paymentMode;
        this.address = address;
        this.orderDate = orderDate;
    }

    // Getters and Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    @Override
    public String toString() {
        return "Order{orderId=" + orderId + ", userId=" + userId + ", totalAmount=" + totalAmount + ", status='" + status + "'}";
    }
}
