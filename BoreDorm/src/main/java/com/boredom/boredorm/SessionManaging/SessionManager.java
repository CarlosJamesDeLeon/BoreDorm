package com.boredom.boredorm.SessionManaging;

import com.boredom.boredorm.Models.User;
import java.io.*;

/**
 * SOLID Principle #1 — Single Responsibility Principle (SRP)
 * SessionManager has ONE responsibility: manage the user's session lifecycle.
 * It implements ISessionManager as a Singleton.
 */
public class SessionManager implements ISessionManager {

    private static final String SESSION_FILE = System.getProperty("user.home") + File.separator + "session.dat";
    
    private static final SessionManager instance = new SessionManager();

    private SessionManager() {}

    public static SessionManager getInstance() {
        return instance;
    }

    @Override
    public void saveSession(User user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(SESSION_FILE))) {
            oos.writeObject(user);
            System.out.println("[SessionManager] Session saved → " + SESSION_FILE);
        } catch (IOException e) {
            System.err.println("[SessionManager] Failed to save session: " + e.getMessage());
        }
    }

    @Override
    public User loadSession() {
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

    @Override
    public boolean isSessionActive() {
        return new File(SESSION_FILE).exists();
    }

    @Override
    public void clearSession() {
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

    @Override
    public int getCurrentUserId() {
        User user = loadSession();
        return (user != null) ? user.getUserId() : 0;
    }

    @Override
    public String getCurrentUserRole() {
        User user = loadSession();
        return (user != null) ? user.getRole() : null;
    }

    @Override
    public String getCurrentUsername() {
        User user = loadSession();
        return (user != null) ? user.getUsername() : null;
    }
}