package com.boredom.boredorm.Strategy;

import javafx.scene.control.Button;

/**
 * Strategy Context object that dynamically resolves and executes the active RoleAccessStrategy.
 */
public class RoleAccessContext {

    private RoleAccessStrategy strategy;

    public RoleAccessContext(String role) {
        setRole(role);
    }

    public void setRole(String role) {
        if ("Admin".equalsIgnoreCase(role)) {
            this.strategy = new AdminAccessStrategy();
        } else {
            this.strategy = new TenantAccessStrategy();
        }
    }

    public RoleAccessStrategy getStrategy() {
        return strategy;
    }

    public boolean canAccessAdminFeatures() {
        return strategy.canAccessAdminFeatures();
    }

    public boolean canModifyTenants() {
        return strategy.canModifyTenants();
    }

    public String getWelcomeMessage(String username) {
        return strategy.getWelcomeMessage(username);
    }

    public void applyUiPermissions(Button navTenants, Button navRooms, Button navBilling) {
        strategy.applyUiPermissions(navTenants, navRooms, navBilling);
    }
}
