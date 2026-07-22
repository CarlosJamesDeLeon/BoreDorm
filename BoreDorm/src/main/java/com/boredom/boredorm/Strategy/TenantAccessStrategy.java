package com.boredom.boredorm.Strategy;

import javafx.scene.control.Button;

/**
 * Concrete strategy implementing permissions and UI rules for Tenant accounts.
 */
public class TenantAccessStrategy implements RoleAccessStrategy {

    @Override
    public boolean canAccessAdminFeatures() {
        return false;
    }

    @Override
    public boolean canModifyTenants() {
        return false;
    }

    @Override
    public String getWelcomeMessage(String username) {
        return "Tenant Portal — Welcome, " + username;
    }

    @Override
    public void applyUiPermissions(Button navTenants, Button navRooms, Button navBilling) {
        // Restrict administrative navigation buttons for tenant users
        if (navTenants != null) navTenants.setVisible(false);
        if (navRooms != null) navRooms.setVisible(false);
        if (navBilling != null) navBilling.setVisible(false);
    }
}
