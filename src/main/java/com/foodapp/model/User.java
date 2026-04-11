package com.foodapp.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * User POJO — Maps to the 'users' table.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String role;
    private Timestamp createdDate;

    // No-arg constructor
    public User() {}

    // Parameterized constructor (without id and createdDate — auto-generated)
    public User(String name, String username, String password, String email, String phone, String address, String role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    // Full constructor
    public User(int id, String name, String username, String password, String email, String phone, String address, String role, Timestamp createdDate) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.createdDate = createdDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Timestamp getCreatedDate() { return createdDate; }
    public void setCreatedDate(Timestamp createdDate) { this.createdDate = createdDate; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', username='" + username + "', role='" + role + "'}";
    }
}
