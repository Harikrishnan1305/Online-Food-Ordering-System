package com.foodapp.model;

import java.io.Serializable;

/**
 * OrderItem POJO — Maps to the 'order_items' table.
 */
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private int orderItemId;
    private int orderId;
    private int menuId;
    private int quantity;
    private double price;

    // Transient display field
    private String menuName;

    // No-arg constructor
    public OrderItem() {}

    // Parameterized constructor (for creating from cart)
    public OrderItem(int orderId, int menuId, int quantity, double price) {
        this.orderId = orderId;
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    // Full constructor
    public OrderItem(int orderItemId, int orderId, int menuId, int quantity, double price) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public int getOrderItemId() { return orderItemId; }
    public void setOrderItemId(int orderItemId) { this.orderItemId = orderItemId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getMenuId() { return menuId; }
    public void setMenuId(int menuId) { this.menuId = menuId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getMenuName() { return menuName; }
    public void setMenuName(String menuName) { this.menuName = menuName; }

    public double getSubtotal() {
        return this.price * this.quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{orderItemId=" + orderItemId + ", menuId=" + menuId + ", qty=" + quantity + ", price=" + price + "}";
    }
}
