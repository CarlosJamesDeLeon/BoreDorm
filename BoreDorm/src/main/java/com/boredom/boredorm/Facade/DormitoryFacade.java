package com.boredom.boredorm.Facade;

import com.boredom.boredorm.DAO.MaintenanceRequestDAO;
import com.boredom.boredorm.DAO.MaintenanceRequestDAOImpl;
import com.boredom.boredorm.DAO.UserDAO;
import com.boredom.boredorm.DAO.UserDAOImpl;
import com.boredom.boredorm.Factory.UserFactory;
import com.boredom.boredorm.Models.MaintenanceRequest;
import com.boredom.boredorm.Models.User;
import com.boredom.boredorm.SessionManaging.SessionManager;
import com.boredom.boredorm.Strategy.RoleAccessContext;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

/**
 * ===========================================================================
 * DESIGN PATTERN: STRUCTURAL — FACADE PATTERN
 * ===========================================================================
 * Purpose: Provides a simplified, unified interface to a set of complex
 * subsystems (UserDAO, MaintenanceRequestDAO, SessionManager serialization,
 * BCrypt hashing, UserFactory, and RoleAccessContext strategy).
 *
 * Benefits: Controllers invoke high-level operations through this Facade
 * without needing to manage multi-step database, security, and serialization
 * dependencies directly.
 */
public class DormitoryFacade {

    private static final DormitoryFacade instance = new DormitoryFacade();
    private final UserDAO userDAO;
    private final MaintenanceRequestDAO maintenanceDAO;

    private DormitoryFacade() {
        this.userDAO = new UserDAOImpl();
        this.maintenanceDAO = new MaintenanceRequestDAOImpl();
    }

    public static DormitoryFacade getInstance() {
        return instance;
    }

    // =========================================================
    //  Authentication & Session Management
    // =========================================================

    /**
     * Facade method for User Authentication.
     * Combines DB retrieval, BCrypt password verification, and Session Serialization.
     */
    public User authenticate(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            return null;
        }
        User user = userDAO.getUserByUsername(username.trim());
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            SessionManager.getInstance().saveSession(user);
            System.out.println("[DormitoryFacade] User authenticated & session serialized: " + user.getUsername());
            return user;
        }
        return null;
    }

    /**
     * Facade method for Tenant Registration.
     * Combines BCrypt hashing, UserFactory creation, and DAO persistence.
     */
    public boolean registerTenant(String username, String rawPassword) {
        if (username == null || username.trim().isEmpty() || rawPassword == null || rawPassword.trim().isEmpty()) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        User newTenant = UserFactory.createTenant(username.trim(), hashedPassword);
        return userDAO.createUser(newTenant);
    }

    /** Facade method to terminate the active session and delete session.dat. */
    public void logout() {
        SessionManager.getInstance().clearSession();
        System.out.println("[DormitoryFacade] User logged out and session deleted.");
    }

    /** Facade method to check if a valid session file exists. */
    public boolean isSessionActive() {
        return SessionManager.getInstance().isSessionActive();
    }

    /** Facade method to retrieve the currently logged-in User from session.dat. */
    public User getActiveUser() {
        return SessionManager.getInstance().loadSession();
    }

    /** Facade method to get the active role strategy context for UI permission rules. */
    public RoleAccessContext getRoleAccessContext() {
        String role = SessionManager.getInstance().getCurrentUserRole();
        return new RoleAccessContext(role);
    }

    /** Facade method to fetch all registered users. */
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    // =========================================================
    //  Maintenance Request Operations (Tenant ↔ Admin)
    // =========================================================

    /**
     * Tenant submits a new maintenance request.
     * Called from TenantDashboardController.
     */
    public boolean submitMaintenanceRequest(MaintenanceRequest request) {
        System.out.println("[DormitoryFacade] Tenant submitting maintenance request: " + request.getDescription());
        return maintenanceDAO.submitRequest(request);
    }

    /**
     * Get all maintenance requests submitted by the currently logged-in tenant.
     * Called from TenantDashboardController to show the tenant their own submissions.
     */
    public List<MaintenanceRequest> getMyMaintenanceRequests() {
        User user = getActiveUser();
        if (user == null) return List.of();
        return maintenanceDAO.getRequestsByUserId(user.getUserId());
    }

    /**
     * Get ALL maintenance requests across all tenants.
     * Called from MaintenanceController (admin view).
     */
    public List<MaintenanceRequest> getAllMaintenanceRequests() {
        return maintenanceDAO.getAllRequests();
    }

    /**
     * Admin updates the status of a maintenance request.
     * Called from MaintenanceController.
     */
    public boolean updateMaintenanceStatus(int requestId, String newStatus, String assignedStaff) {
        return maintenanceDAO.updateRequestStatus(requestId, newStatus, assignedStaff);
    }

    /**
     * Count of pending requests — used by admin Dashboard summary card.
     */
    public int countPendingMaintenanceRequests() {
        return maintenanceDAO.countPendingRequests();
    }
}
