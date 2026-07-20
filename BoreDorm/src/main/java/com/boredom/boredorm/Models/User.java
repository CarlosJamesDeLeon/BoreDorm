package com.boredom.boredorm.Models;

import java.io.Serializable;

// SOLID - SRP: User is a pure data model. It holds state only, no business logic.
// Serializable is required so this object can be written to session.dat
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int userId;
    private String username;
    private String password; // Will be hashed
    private String role;     // Will be admin or tenant
    private String roomNumber;  // Added field
    private String leaseStatus; // Added field

    // Updated Constructor to support all directory fields
    public User(int userId, String username, String password, String role, String roomNumber, String leaseStatus) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.roomNumber = roomNumber;
        this.leaseStatus = leaseStatus;
    }

    // Standard Getters to read the data
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getRoomNumber() { return roomNumber; }  // Added getter
    public String getLeaseStatus() { return leaseStatus; } // Added getter

    // Standard Setters to update the data
    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }   // Added setter
    public void setLeaseStatus(String leaseStatus) { this.leaseStatus = leaseStatus; } // Added setter

    public User(){};
}