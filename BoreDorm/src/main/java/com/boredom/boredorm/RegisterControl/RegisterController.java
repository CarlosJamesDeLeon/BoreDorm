package com.boredom.boredorm.RegisterControl;

import com.boredom.boredorm.DAO.UserDAO;
import com.boredom.boredorm.DAO.UserDAOImpl;
import com.boredom.boredorm.Models.User;
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

    private UserDAO userDAO;

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

        // Initialize our DAO layer
        userDAO = new UserDAOImpl();
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

        // 1. Hash password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // 2. Create our Model instance
        User newUser = new User(0, username.trim(), hashedPassword, "tenant");

        // 3. Call the DAO to CREATE the user in the database
        boolean success = userDAO.createUser(newUser);

        if (success) {
            hideError();
            NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
        } else {
            showError("Username may already exist or system error.");
        }
    }

    @FXML
    private void handleGoToLogin(ActionEvent event) {
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    private void hideError() {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }
}