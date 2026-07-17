package com.boredom.boredorm.SessionManaging;

import java.io.*;
import java.nio.file.*;
import java.util.Properties;
import java.util.UUID;

public class SessionManager {
    private static final String FILE_NAME = ".boredorm_session";
    private static final Path SESSION_FILE = Paths.get(System.getProperty("user.home"), FILE_NAME);


    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    // Saves the session token locally on the machine
    public static void saveSessionLocally(String token) {
        Properties props = new Properties();
        props.setProperty("session_token", token);
        try (OutputStream out = Files.newOutputStream(SESSION_FILE)) {
            props.store(out, "BoreDorm User Session");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Reads the local session token if it exists
    public static String getLocalSessionToken() {
        if (!Files.exists(SESSION_FILE)) {
            return null;
        }
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(SESSION_FILE)) {
            props.load(in);
            return props.getProperty("session_token");
        } catch (IOException e) {
            return null;
        }
    }

    // Deletes the local file when the user logs out
    public static void clearLocalSession() {
        try {
            Files.deleteIfExists(SESSION_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}