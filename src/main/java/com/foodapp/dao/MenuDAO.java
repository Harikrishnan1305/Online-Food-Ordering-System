package com.foodapp.dao;

import com.foodapp.model.Menu;
import java.util.List;

/**
 * MenuDAO — Data Access Object interface for Menu entity.
 */
public interface MenuDAO {

    /**
     * Add a new menu item.
     */
    boolean addMenu(Menu menu);

    /**
     * Get menu item by primary key ID.
     */
    Menu getMenuById(int id);

    /**
     * Get all menu items for a specific restaurant.
     */
    List<Menu> getMenuByRestaurantId(int restaurantId);

    /**
     * Update a menu item.
     */
    boolean updateMenu(Menu menu);

    /**
     * Delete a menu item by ID.
     */
    boolean deleteMenu(int id);

    /**
     * Search menu items by name across all restaurants.
     */
    List<Menu> searchMenu(String keyword);
}
