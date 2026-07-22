package com.boredom.boredorm.Strategy;

import javafx.scene.control.Button;

/**
 * Concrete strategy implementing permissions and UI rules for Administrator accounts.
 */
public class AdminAccessStrategy implements RoleAccessStrategy {

    @Override
    public boolean canAccessAdminFeatures() {
        return true;
    }

    @Override
    public boolean canModifyTenants() {
        return true;
    }

    @Override
    public String getWelcomeMessage(String username) {
        return "System Administrator Portal — Welcome, " + username;
    }

    @Override
    public void applyUiPermissions(Button navTenants, Button navRooms, Button navBilling) {
        // Admin has full visibility of all administrative navigation controls
        if (navTenants != null) navTenants.setVisible(true);
        if (navRooms != null) navRooms.setVisible(true);
        if (navBilling != null) navBilling.setVisible(true);
    }
}
