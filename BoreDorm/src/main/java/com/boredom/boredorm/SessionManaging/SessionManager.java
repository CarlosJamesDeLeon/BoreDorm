package com.boredom.boredorm.SessionManaging;

import com.boredom.boredorm.Models.User;

import java.io.*;

/**
 * SOLID Principle #1 — Single Responsibility Principle (SRP)
 *
 * SessionManager has ONE responsibility: manage the user's session lifecycle.
 * It creates, reads, validates, and deletes the serialized session file (session.dat).
 * It does NOT handle UI navigation, database queries, or business logic.
 *
 * Benefit: Changes to session storage (e.g., switching from file to DB) only
 * affect this one class, not controllers or DAOs.
 *
 * SOLID Principle #2 — Dependency Inversion Principle (DIP)
 * Implements ISessionManager so controllers depend on the interface abstraction,
 * not this concrete class directly.
 */
public class SessionManager implements ISessionManager {

    // Session file stored in the user's home directory for cross-platform support
    private static final String SESSION_FILE = System.getProperty("user.home") + File.separator + "session.dat";

    // -----------------------------------------------------------------------
    // SAVE — Serializes the logged-in User object to session.dat
    // -----------------------------------------------------------------------
    public static void saveSession(User user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(SESSION_FILE))) {
            oos.writeObject(user);
            System.out.println("[SessionManager] Session saved → " + SESSION_FILE);
        } catch (IOException e) {
            System.err.println("[SessionManager] Failed to save session: " + e.getMessage());
        }
    }

    // -----------------------------------------------------------------------
    // LOAD — Deserializes User from session.dat; returns null if missing/corrupt
    // -----------------------------------------------------------------------
    public static User loadSession() {
        File file = new File(SESSION_FILE);
        if (!file.exists()) {
            System.out.println("[SessionManager] No session file found.");
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {
            User user = (User) ois.readObject();
            System.out.println("[SessionManager] Session loaded for: " + user.getUsername());
            return user;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[SessionManager] Corrupted session file, clearing: " + e.getMessage());
            clearSession();
            return null;
        }
    }

    // -----------------------------------------------------------------------
    // IS ACTIVE — Check whether a valid session file exists
    // -----------------------------------------------------------------------
    public static boolean isSessionActive() {
        return new File(SESSION_FILE).exists();
    }

    // -----------------------------------------------------------------------
    // CLEAR — Deletes session.dat on logout
    // -----------------------------------------------------------------------
    public static void clearSession() {
        File file = new File(SESSION_FILE);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("[SessionManager] Session file deleted (logout complete).");
            } else {
                System.err.println("[SessionManager] Warning: Could not delete session file.");
            }
        }
    }

    // -----------------------------------------------------------------------
    // CONVENIENCE GETTERS — Read current user info without re-deserializing
    // -----------------------------------------------------------------------
    public static int getCurrentUserId() {
        User user = loadSession();
        return (user != null) ? user.getUserId() : 0;
    }

    public static String getCurrentUserRole() {
        User user = loadSession();
        return (user != null) ? user.getRole() : null;
    }

    public static String getCurrentUsername() {
        User user = loadSession();
        return (user != null) ? user.getUsername() : null;
    }

    // Legacy compatibility method
    public static void clearLocalSession() {
        clearSession();
    }
}