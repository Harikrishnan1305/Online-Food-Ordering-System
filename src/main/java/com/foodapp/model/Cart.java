package com.foodapp.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Cart — Session-based shopping cart.
 * Uses HashMap<Integer, CartItem> where key = menuId for O(1) operations.
 * 
 * KEY LOGIC:
 * - First item   → creates the cart with that restaurant
 * - Same item    → increases quantity
 * - Diff restaurant → resets the cart, then adds the new item
 */
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    private int restaurantId;
    private String restaurantName;
    private Map<Integer, CartItem> items;  // menuId → CartItem

    // No-arg constructor
    public Cart() {
        this.items = new HashMap<>();
    }

    /**
     * Add an item to the cart.
     * If the item's restaurant differs from the current cart's restaurant,
     * the cart is cleared first (restaurant switch).
     *
     * @return true if cart was reset due to restaurant change
     */
    public boolean addItem(CartItem item) {
        boolean wasReset = false;

        // If cart is empty, set the restaurant context
        if (items.isEmpty()) {
            this.restaurantId = item.getRestaurantId();
            this.restaurantName = item.getRestaurantName();
        }
        // If different restaurant → reset cart
        else if (this.restaurantId != item.getRestaurantId()) {
            this.items.clear();
            this.restaurantId = item.getRestaurantId();
            this.restaurantName = item.getRestaurantName();
            wasReset = true;
        }

        // If the same item already exists, increase quantity
        if (items.containsKey(item.getMenuId())) {
            CartItem existing = items.get(item.getMenuId());
            existing.setQuantity(existing.getQuantity() + 1);
        } else {
            item.setQuantity(1);
            items.put(item.getMenuId(), item);
        }

        return wasReset;
    }

    /**
     * Remove an item from the cart by menuId.
     */
    public void removeItem(int menuId) {
        items.remove(menuId);
        if (items.isEmpty()) {
            this.restaurantId = 0;
            this.restaurantName = null;
        }
    }

    /**
     * Update the quantity of a specific item.
     * If quantity reaches 0 or below, the item is removed.
     */
    public void updateQuantity(int menuId, int quantity) {
        if (quantity <= 0) {
            removeItem(menuId);
        } else if (items.containsKey(menuId)) {
            items.get(menuId).setQuantity(quantity);
        }
    }

    /**
     * Get the total price of all items in the cart.
     */
    public double getTotal() {
        double total = 0;
        for (CartItem item : items.values()) {
            total += item.getSubtotal();
        }
        return total;
    }

    /**
     * Get the total number of items in the cart (sum of quantities).
     */
    public int getItemCount() {
        int count = 0;
        for (CartItem item : items.values()) {
            count += item.getQuantity();
        }
        return count;
    }

    /**
     * Clear all items from the cart.
     */
    public void clear() {
        items.clear();
        this.restaurantId = 0;
        this.restaurantName = null;
    }

    /**
     * Check if the cart is empty.
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Get all cart items as a collection.
     */
    public Collection<CartItem> getCartItems() {
        return items.values();
    }

    // Getters and Setters
    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public Map<Integer, CartItem> getItems() { return items; }
    public void setItems(Map<Integer, CartItem> items) { this.items = items; }

    @Override
    public String toString() {
        return "Cart{restaurant='" + restaurantName + "', items=" + items.size() + ", total=" + getTotal() + "}";
    }
}
