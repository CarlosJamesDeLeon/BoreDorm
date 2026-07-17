package com.boredom.boredorm.LoginControl;

import com.boredom.boredorm.DAO.UserDAO;
import com.boredom.boredorm.DAO.UserDAOImpl;
import com.boredom.boredorm.Models.User;
import com.boredom.boredorm.SessionManaging.SessionManager;
import com.boredom.boredorm.DBConnection;
import com.boredom.boredorm.NavigationUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.event.ActionEvent;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
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

    private ContextMenu autocompletePopup;
    private UserDAO userDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titleLabel.setId("titleLabel");
        usernameLabel.setId("usernameLabel");
        usernameField.setId("usernameField");
        passwordLabel.setId("passwordLabel");
        passwordField.setId("passwordField");
        signInButton.setId("signInButton");
        goToRegisterButton.setId("goToLoginButton");
        errorLabel.setId("errorLabel");

        userDAO = new UserDAOImpl();
        setupAutocomplete();
    }

    private void setupAutocomplete() {
        autocompletePopup = new ContextMenu();
        autocompletePopup.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #0A0A0A; -fx-border-width: 1; -fx-padding: 0;");

        usernameField.setOnMouseClicked(event -> {
            if (autocompletePopup != null && autocompletePopup.isShowing()) {
                autocompletePopup.hide();
            } else if (autocompletePopup != null) {
                List<User> allUsers = userDAO.getAllUsers();
                if (!allUsers.isEmpty()) {
                    autocompletePopup.getItems().clear();
                    for (User u : allUsers) {
                        MenuItem item = new MenuItem(u.getUsername());
                        item.setStyle("-fx-font-family: 'JetBrains Mono'; -fx-font-size: 12; -fx-text-fill: #0A0A0A; -fx-padding: 8 16;");
                        item.setOnAction(e -> usernameField.setText(u.getUsername()));
                        autocompletePopup.getItems().add(item);
                    }
                    autocompletePopup.show(usernameField, javafx.geometry.Side.BOTTOM, 0, 0);
                }
            }
        });

        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (autocompletePopup != null && autocompletePopup.isShowing()) {
                autocompletePopup.hide();
            }
        });
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleLogin(new ActionEvent(event.getSource(), event.getTarget()));
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            showError("Username and password are required.");
            return;
        }

        User user = userDAO.getUserByUsername(username.trim());

        if (user != null) {
            if (BCrypt.checkpw(password, user.getPassword())) {
                hideError();
                String sessionToken = SessionManager.generateToken();
                saveSessionToDatabase(user.getUserId(), sessionToken);
                SessionManager.saveSessionLocally(sessionToken);

                if ("admin".equals(user.getRole())) {
                    NavigationUtil.navigateTo(event, "/com/boredom/boredorm/dashboard.fxml");
                } else {
                    NavigationUtil.navigateTo(event, "/com/boredom/boredorm/tenant_dashboard.fxml");
                }
            } else {
                showError("Incorrect username or password");
            }
        } else {
            showError("Incorrect username or password");
        }
    }

    private void saveSessionToDatabase(int userId, String token) {
        String query = "INSERT INTO user_sessions (user_id, session_token) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, token);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoToRegister(ActionEvent event) {
        if (autocompletePopup != null && autocompletePopup.isShowing()) {
            autocompletePopup.hide();
        }
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/register.fxml");
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