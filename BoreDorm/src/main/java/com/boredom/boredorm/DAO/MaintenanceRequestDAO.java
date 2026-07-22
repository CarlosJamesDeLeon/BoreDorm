package com.boredom.boredorm.DAO;

import com.boredom.boredorm.Models.MaintenanceRequest;
import java.util.List;

/**
 * DAO interface for maintenance request operations.
 * Follows the Dependency Inversion Principle (DIP) — controllers depend on this
 * abstraction, not on the concrete implementation.
 */
public interface MaintenanceRequestDAO {
    /**
     * Submit a new maintenance request from a tenant.
     */
    boolean submitRequest(MaintenanceRequest request);

    /**
     * Get all requests submitted by a specific tenant (by userId).
     */
    List<MaintenanceRequest> getRequestsByUserId(int tenantUserId);

    /**
     * Get all maintenance requests (for admin view).
     */
    List<MaintenanceRequest> getAllRequests();

    /**
     * Update the status and assigned staff for a request (admin action).
     */
    boolean updateRequestStatus(int requestId, String newStatus, String assignedStaff);

    /**
     * Count pending requests (used by admin dashboard summary card).
     */
    int countPendingRequests();
}
