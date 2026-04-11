package com.foodapp.model;

import java.io.Serializable;

/**
 * Menu POJO — Maps to the 'menu' table.
 */
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int restaurantId;
    private String name;
    private String description;
    private double price;
    private double rating;
    private String imageUrl;
    private boolean isAvailable;

    // Transient field — not stored in DB, loaded for display
    private String restaurantName;

    // No-arg constructor
    public Menu() {}

    // Parameterized constructor
    public Menu(int restaurantId, String name, String description, double price, double rating, String imageUrl, boolean isAvailable) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.isAvailable = isAvailable;
    }

    // Full constructor
    public Menu(int id, int restaurantId, String name, String description, double price, double rating, String imageUrl, boolean isAvailable) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.isAvailable = isAvailable;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    @Override
    public String toString() {
        return "Menu{id=" + id + ", name='" + name + "', price=" + price + ", restaurantId=" + restaurantId + "}";
    }
}
