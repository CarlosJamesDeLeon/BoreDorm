package com.boredom.boredorm.Models;

public class ActivityLog {
    private final String tenantName;
    private final String actionLogged;
    private final String timestamp;

    public ActivityLog(String tenantName, String actionLogged, String timestamp) {
        this.tenantName = tenantName;
        this.actionLogged = actionLogged;
        this.timestamp = timestamp;
    }

    public String getTenantName() { return tenantName; }
    public String getActionLogged() { return actionLogged; }
    public String getTimestamp() { return timestamp; }
}