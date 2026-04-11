package com.foodapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection — Utility class for MySQL database connections.
 * Uses DriverManager with configurable credentials.
 */
public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/food_ordering_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "YOUR_MYSQL_PASSWORD";

    // Load MySQL JDBC Driver once
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
            throw new RuntimeException("Failed to load MySQL driver", e);
        }
    }

    /**
     * Get a new database connection.
     * Each call returns a fresh connection — caller is responsible for closing it.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /**
     * Safely close a connection (null-safe).
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
