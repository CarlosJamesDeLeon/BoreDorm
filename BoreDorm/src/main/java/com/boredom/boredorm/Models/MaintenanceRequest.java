package com.boredom.boredorm.Models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Model representing a maintenance request submitted by a tenant.
 * Implements Serializable for session compatibility.
 */
public class MaintenanceRequest implements Serializable {
    private static final long serialVersionUID = 2L;

    private int requestId;
    private int tenantUserId;
    private String tenantUsername;
    private String roomNumber;
    private String issueType;
    private String description;
    private String status;      // "Pending", "In Progress", "Resolved"
    private String dateFiled;
    private String assignedStaff;

    public MaintenanceRequest() {}

    public MaintenanceRequest(int requestId, int tenantUserId, String tenantUsername,
                              String roomNumber, String issueType, String description,
                              String status, String dateFiled, String assignedStaff) {
        this.requestId = requestId;
        this.tenantUserId = tenantUserId;
        this.tenantUsername = tenantUsername;
        this.roomNumber = roomNumber;
        this.issueType = issueType;
        this.description = description;
        this.status = status;
        this.dateFiled = dateFiled;
        this.assignedStaff = assignedStaff;
    }

    public int getRequestId() { return requestId; }
    public void setRequestId(int requestId) { this.requestId = requestId; }

    public int getTenantUserId() { return tenantUserId; }
    public void setTenantUserId(int tenantUserId) { this.tenantUserId = tenantUserId; }

    public String getTenantUsername() { return tenantUsername; }
    public void setTenantUsername(String tenantUsername) { this.tenantUsername = tenantUsername; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getIssueType() { return issueType; }
    public void setIssueType(String issueType) { this.issueType = issueType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDateFiled() { return dateFiled; }
    public void setDateFiled(String dateFiled) { this.dateFiled = dateFiled; }

    public String getAssignedStaff() { return assignedStaff; }
    public void setAssignedStaff(String assignedStaff) { this.assignedStaff = assignedStaff; }
}
