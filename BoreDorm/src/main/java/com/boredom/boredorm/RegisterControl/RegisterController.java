package com.boredom.boredorm.RegisterControl;

import com.boredom.boredorm.DBConnection;
import com.boredom.boredorm.NavigationUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML private Label titleLabel;
    @FXML private Label usernameLabel;
    @FXML private TextField usernameField;
    @FXML private Label passwordLabel;
    @FXML private PasswordField passwordField;
    @FXML private Label confirmPasswordLabel;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button signUpButton;
    @FXML private Button goToLoginButton;
    @FXML private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titleLabel.setId("titleLabel");
        usernameLabel.setId("usernameLabel");
        usernameField.setId("usernameField");
        passwordLabel.setId("passwordLabel");
        passwordField.setId("passwordField");
        confirmPasswordLabel.setId("confirmPasswordLabel");
        confirmPasswordField.setId("confirmPasswordField");
        signUpButton.setId("signUpButton");
        goToLoginButton.setId("goToLoginButton");
        errorLabel.setId("errorLabel");
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty() ||
                confirmPassword == null || confirmPassword.trim().isEmpty()) {
            showError("All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            if (usernameExists(conn, username.trim())) {
                showError("Username is already taken.");
                return;
            }

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";

            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, username.trim());
                insertStmt.setString(2, hashedPassword);
                insertStmt.executeUpdate();

                errorLabel.setVisible(false);
                NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
            }
        } catch (SQLException e) {
            showError("Database integration failed.");
        }
    }

    @FXML
    private void handleGoToLogin(ActionEvent event) {
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
    }

    private boolean usernameExists(Connection conn, String username) throws SQLException {
        String query = "SELECT 1 FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}