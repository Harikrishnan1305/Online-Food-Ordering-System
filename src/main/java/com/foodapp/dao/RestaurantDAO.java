package com.foodapp.dao;

import com.foodapp.model.Restaurant;
import java.util.List;

/**
 * RestaurantDAO — Data Access Object interface for Restaurant entity.
 */
public interface RestaurantDAO {

    /**
     * Add a new restaurant.
     */
    boolean addRestaurant(Restaurant restaurant);

    /**
     * Get restaurant by primary key ID.
     */
    Restaurant getRestaurantById(int id);

    /**
     * Get all active restaurants.
     */
    List<Restaurant> getAllRestaurants();

    /**
     * Search restaurants by name or cuisine keyword.
     */
    List<Restaurant> searchRestaurants(String keyword);

    /**
     * Update restaurant details.
     */
    boolean updateRestaurant(Restaurant restaurant);

    /**
     * Delete a restaurant by ID.
     */
    boolean deleteRestaurant(int id);
}
