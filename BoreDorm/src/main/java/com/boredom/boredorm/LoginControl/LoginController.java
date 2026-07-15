package com.boredom.boredorm.LoginControl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
public class LoginController {
    @FXML private Label titleLabel;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button signInButton;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            errorLabel.setText("Username and password are required.");
            errorLabel.setVisible(true);
            return;
        }
        if ("admin".equals(username) && "password".equals(password)) {
            errorLabel.setVisible(false);
            com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/dashboard.fxml");
        } else {
            errorLabel.setText("Incorrect username or password");
            errorLabel.setVisible(true);
        }
    }
}
