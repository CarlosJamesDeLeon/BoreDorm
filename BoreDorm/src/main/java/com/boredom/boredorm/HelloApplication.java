package com.boredom.boredorm;

import com.boredom.boredorm.SessionManaging.SessionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        String localToken = SessionManager.getLocalSessionToken();
        String initialFxml = "/com/boredom/boredorm/login.fxml"; // Default target

        if (localToken != null) {
            // Check session validity AND retrieve the user's role
            String query = "SELECT u.role FROM user_sessions s JOIN users u ON s.user_id = u.userID WHERE s.session_token = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, localToken);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String role = rs.getString("role");
                        if ("admin".equals(role)) {
                            initialFxml = "/com/boredom/boredorm/dashboard.fxml";
                        } else {
                            initialFxml = "/com/boredom/boredorm/tenant_dashboard.fxml";
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(initialFxml));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("BoreDorm");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}