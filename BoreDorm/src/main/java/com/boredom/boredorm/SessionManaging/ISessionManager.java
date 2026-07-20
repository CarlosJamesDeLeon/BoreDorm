package com.boredom.boredorm.SessionManaging;

import com.boredom.boredorm.Models.User;

/**
 * SOLID Principle #2 — Dependency Inversion Principle (DIP)
 *
 * Controllers depend on this ABSTRACTION (interface), not on the concrete
 * SessionManager class directly. This means you can swap the session
 * implementation (e.g., file-based → database-based) without changing
 * any controller code.
 *
 * Classes that use this: LoginController, DashboardController,
 * TenantDashboardController, and any future controller.
 *
 * Benefit: Low coupling between controllers and the session storage mechanism.
 */
public interface ISessionManager {
    void saveSession(User user);
    User loadSession();
    boolean isSessionActive();
    void clearSession();
    int getCurrentUserId();
    String getCurrentUserRole();
    String getCurrentUsername();
}
