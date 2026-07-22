package com.boredom.boredorm.LoginControl;

import com.boredom.boredorm.Facade.DormitoryFacade;
import com.boredom.boredorm.NavigationUtil;
import com.boredom.boredorm.Models.User;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button signInButton;

    private final List<String> registeredUsernames = new ArrayList<>();
    private final ContextMenu autocompleteMenu = new ContextMenu();

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            if (usernameField != null) {
                usernameField.requestFocus();
            }

            try {
                List<User> users = DormitoryFacade.getInstance().getAllUsers();
                registeredUsernames.clear();
                for (User u : users) {
                    if (u.getUsername() != null && !u.getUsername().trim().isEmpty()) {
                        registeredUsernames.add(u.getUsername().trim());
                    }
                }
                System.out.println("Synchronized " + registeredUsernames.size() + " accounts from database.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            showAutocompletePopup(newValue);
        });
    }

    @FXML
    private void handleUsernameClick(MouseEvent event) {
        showAutocompletePopup(usernameField.getText());
    }

    private void showAutocompletePopup(String currentText) {
        autocompleteMenu.getItems().clear();

        String filter = (currentText == null) ? "" : currentText.trim().toLowerCase();

        for (String name : registeredUsernames) {
            if (filter.isEmpty() || name.toLowerCase().contains(filter)) {
                MenuItem item = new MenuItem(name);
                item.setStyle("-fx-font-family: 'JetBrains Mono'; -fx-font-size: 12;");
                item.setOnAction(e -> {
                    usernameField.setText(name);
                    passwordField.requestFocus();
                    autocompleteMenu.hide();
                });
                autocompleteMenu.getItems().add(item);
            }
        }

        if (!autocompleteMenu.getItems().isEmpty()) {
            if (!autocompleteMenu.isShowing()) {
                autocompleteMenu.show(usernameField, javafx.geometry.Side.BOTTOM, 0, 0);
            }
        } else {
            autocompleteMenu.hide();
        }
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        processLogin(event);
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            processLogin(new ActionEvent(event.getSource(), event.getTarget()));
        }
    }

    @FXML
    private void handleGoToRegister(ActionEvent event) {
        autocompleteMenu.hide();
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/register.fxml");
    }

    private void processLogin(ActionEvent event) {
        autocompleteMenu.hide();

        String inputUsername = usernameField.getText();
        String inputPassword = passwordField.getText();

        if (inputUsername == null || inputUsername.trim().isEmpty() || inputPassword == null || inputPassword.isEmpty()) {
            showError("Missing Fields", "Please enter both username and password.");
            return;
        }

        inputUsername = inputUsername.trim();

        try {
            // ✅ STRUCTURAL FACADE: Delegate authentication, password checking & session serialization to DormitoryFacade
            User user = DormitoryFacade.getInstance().authenticate(inputUsername, inputPassword);

            if (user != null) {
                if ("Admin".equalsIgnoreCase(user.getRole())) {
                    NavigationUtil.navigateTo(event, "/com/boredom/boredorm/dashboard.fxml");
                } else {
                    NavigationUtil.navigateTo(event, "/com/boredom/boredorm/tenant_dashboard.fxml");
                }
            } else {
                showError("Authentication Failed", "Invalid username or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Database Error", "Unable to establish connection to system services.");
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}