package com.boredom.boredorm.Models;

public class TenantProfile {
    private String username;
    private String roomNumber;
    private String status;

    public TenantProfile(String username, String roomNumber, String status) {
        this.username = username;
        this.roomNumber = roomNumber;
        this.status = status;
    }

    // Getters
    public String getUsername() { return username; }
    public String getRoomNumber() { return roomNumber; }
    public String getStatus() { return status; }

    // Setters added for updating UI/Model
    public void setUsername(String username) { this.username = username; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public void setStatus(String status) { this.status = status; }
}