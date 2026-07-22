package com.boredom.boredorm.Factory;

import com.boredom.boredorm.Models.User;

/**
 * ===========================================================================
 * DESIGN PATTERN: CREATIONAL — FACTORY METHOD / SIMPLE FACTORY PATTERN
 * ===========================================================================
 * Purpose: Encapsulates object creation logic for User entities (Admin vs Tenant).
 * Prevents direct instantiation boilerplate across controllers and ensures
 * consistent initial states, roles, and default room/lease properties.
 */
public class UserFactory {

    /**
     * Factory method to create a new Tenant User with default pending/unassigned values.
     */
    public static User createTenant(String username, String hashedPassword) {
        return new User(0, username, hashedPassword, "tenant", "Unassigned", "Pending");
    }

    /**
     * Factory method to create a new Admin User with active status.
     */
    public static User createAdmin(String username, String hashedPassword) {
        return new User(0, username, hashedPassword, "Admin", "N/A", "Active");
    }

    /**
     * Factory method to construct a User object with custom room and lease status.
     */
    public static User createUser(int userId, String username, String hashedPassword, String role, String roomNumber, String leaseStatus) {
        String finalRoom = (roomNumber == null || roomNumber.trim().isEmpty()) ? "Unassigned" : roomNumber.trim();
        String finalStatus = (leaseStatus == null || leaseStatus.trim().isEmpty()) ? "Pending" : leaseStatus.trim();
        return new User(userId, username, hashedPassword, role, finalRoom, finalStatus);
    }
}
