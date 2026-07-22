package com.boredom.boredorm.Strategy;

import javafx.scene.control.Button;

/**
 * ===========================================================================
 * DESIGN PATTERN: BEHAVIORAL — STRATEGY PATTERN
 * ===========================================================================
 * Purpose: Defines a common interface for different role-based access control
 * algorithms (Admin vs Tenant). Encapsulates permission checking and UI layout
 * adjustments dynamically based on the active user role.
 */
public interface RoleAccessStrategy {

    /**
     * Determines whether the current role has administrative access privileges.
     */
    boolean canAccessAdminFeatures();

    /**
     * Determines whether the current role can modify tenant records.
     */
    boolean canModifyTenants();

    /**
     * Formats a role-specific welcome message for the dashboard.
     */
    String getWelcomeMessage(String username);

    /**
     * Applies UI restrictions to navigation buttons based on role permissions.
     */
    void applyUiPermissions(Button navTenants, Button navRooms, Button navBilling);
}
