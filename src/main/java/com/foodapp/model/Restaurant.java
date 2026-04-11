package com.foodapp.model;

import java.io.Serializable;

/**
 * Restaurant POJO — Maps to the 'restaurants' table.
 */
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String cuisine;
    private double rating;
    private int deliveryTime;
    private String imageUrl;
    private boolean isActive;

    // No-arg constructor
    public Restaurant() {}

    // Parameterized constructor
    public Restaurant(String name, String cuisine, double rating, int deliveryTime, String imageUrl, boolean isActive) {
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
    }

    // Full constructor
    public Restaurant(int id, String name, String cuisine, double rating, int deliveryTime, String imageUrl, boolean isActive) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCuisine() { return cuisine; }
    public void setCuisine(String cuisine) { this.cuisine = cuisine; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(int deliveryTime) { this.deliveryTime = deliveryTime; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    @Override
    public String toString() {
        return "Restaurant{id=" + id + ", name='" + name + "', cuisine='" + cuisine + "', rating=" + rating + "}";
    }
}
