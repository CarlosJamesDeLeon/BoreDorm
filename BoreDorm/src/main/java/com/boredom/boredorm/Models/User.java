package com.boredom.boredorm.Models;

public class User {
    private int userId;
    private String username;
    private String password; // Will be hashed
    private String role;     // Will be admin or tenant

    // Constructor to build the user object
    public User(int userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Standard Getters to read the data
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    // Standard Setters to update the data
    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
}