package com.boredom.boredorm.LoginControl;

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

public class LoginController implements Initializable {
    @FXML private Label titleLabel;
    @FXML private Label usernameLabel;
    @FXML private TextField usernameField;
    @FXML private Label passwordLabel;
    @FXML private PasswordField passwordField;
    @FXML private Button signInButton;
    @FXML private Button goToRegisterButton;
    @FXML private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titleLabel.setId("titleLabel");
        usernameLabel.setId("usernameLabel");
        usernameField.setId("usernameField");
        passwordLabel.setId("passwordLabel");
        passwordField.setId("passwordField");
        signInButton.setId("signInButton");
        goToRegisterButton.setId("goToRegisterButton");
        errorLabel.setId("errorLabel");
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            showError("Username and password are required.");
            return;
        }

        String query = "SELECT password FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    if (BCrypt.checkpw(password, storedHash)) {
                        errorLabel.setVisible(false);
                        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/dashboard.fxml");
                    } else {
                        showError("Incorrect username or password");
                    }
                } else {
                    showError("Incorrect username or password");
                }
            }
        } catch (SQLException e) {
            showError("Database connection error");
        }
    }

    @FXML
    private void handleGoToRegister(ActionEvent event) {
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/register.fxml");
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}