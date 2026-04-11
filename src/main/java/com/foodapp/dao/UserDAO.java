package com.foodapp.dao;

import com.foodapp.model.User;
import java.util.List;

/**
 * UserDAO — Data Access Object interface for User entity.
 */
public interface UserDAO {

    /**
     * Register a new user.
     */
    boolean addUser(User user);

    /**
     * Get user by primary key ID.
     */
    User getUserById(int id);

    /**
     * Get user by unique username.
     */
    User getUserByUsername(String username);

    /**
     * Validate login credentials.
     * Returns the User object if valid, null otherwise.
     */
    User validateUser(String username, String password);

    /**
     * Update user details.
     */
    boolean updateUser(User user);

    /**
     * Delete a user by ID.
     */
    boolean deleteUser(int id);

    /**
     * Get all users (admin use).
     */
    List<User> getAllUsers();

    /**
     * Check if a username already exists.
     */
    boolean isUsernameExists(String username);
}
