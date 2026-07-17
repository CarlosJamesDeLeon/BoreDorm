package com.boredom.boredorm.TenantDashboardControl;

import com.boredom.boredorm.DBConnection;
import com.boredom.boredorm.NavigationUtil;
import com.boredom.boredorm.SessionManaging.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class TenantDashboardController implements Initializable {

    @FXML private Label roomLabel;
    @FXML private Label nameLabel;
    @FXML private Label statusLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadTenantProfile();
    }

    private void loadTenantProfile() {
        String token = SessionManager.getLocalSessionToken();
        if (token == null) return;

        // Query joining sessions with the users table to isolate the logged-in individual
        String query = "SELECT u.username, u.role FROM user_sessions s " +
                "JOIN users u ON s.user_id = u.userID WHERE s.session_token = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, token);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nameLabel.setText(rs.getString("username"));
                    statusLabel.setText("Active (" + rs.getString("role") + ")");
                    roomLabel.setText("Assigned via System"); // Placeholder until your room table hooks up
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        String localToken = SessionManager.getLocalSessionToken();
        if (localToken != null) {
            String query = "DELETE FROM user_sessions WHERE session_token = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, localToken);
                stmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SessionManager.clearLocalSession();
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
    }
}