package com.foodapp.model;

import java.io.Serializable;

/**
 * CartItem — Represents a single item in the shopping cart.
 * Stored in HttpSession (never persisted to database).
 */
public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private int menuId;
    private String name;
    private double price;
    private int quantity;
    private int restaurantId;
    private String restaurantName;
    private String imageUrl;

    // No-arg constructor
    public CartItem() {}

    // Full constructor
    public CartItem(int menuId, String name, double price, int quantity, int restaurantId, String restaurantName, String imageUrl) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public int getMenuId() { return menuId; }
    public void setMenuId(int menuId) { this.menuId = menuId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    /**
     * Returns the subtotal for this item (price × quantity).
     */
    public double getSubtotal() {
        return this.price * this.quantity;
    }

    @Override
    public String toString() {
        return "CartItem{menuId=" + menuId + ", name='" + name + "', qty=" + quantity + ", price=" + price + "}";
    }
}
