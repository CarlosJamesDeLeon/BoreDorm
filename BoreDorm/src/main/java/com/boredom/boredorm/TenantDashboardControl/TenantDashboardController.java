package com.boredom.boredorm.TenantDashboardControl;

import com.boredom.boredorm.Facade.DormitoryFacade;
import com.boredom.boredorm.Models.User;
import com.boredom.boredorm.NavigationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;

public class TenantDashboardController {

    // --- Option A: Tenant Portal Fields ---
    @FXML private Button signOutButton;
    @FXML private Label roomLabel;
    @FXML private Label usernameLabel;
    @FXML private Label statusLabel;

    // --- Option B: Directory Management Fields (Kept to prevent FXML parsing errors) ---
    @FXML private VBox sidebar;
    @FXML private Label sidebarTitle;
    @FXML private Button navDashboard;
    @FXML private Button navTenants;
    @FXML private Button navRooms;
    @FXML private Button navBilling;
    @FXML private Button navMaintenance;
    @FXML private Button btnSignOut;

    @FXML private HBox splitPane;
    @FXML private VBox leftPanel;
    @FXML private Label panelTitle;
    @FXML private TextField searchField;
    @FXML private TableView<?> tenantTable;
    @FXML private TableColumn<?, ?> colRoom;
    @FXML private TableColumn<?, ?> colName;
    @FXML private TableColumn<?, ?> colStatus;

    @FXML private VBox rightPanel;
    @FXML private TextField editName;
    @FXML private TextField editContact;
    @FXML private ComboBox<?> comboLeaseStatus;
    @FXML private Label lblFullName;
    @FXML private Label lblContact;
    @FXML private Label lblLeaseStatus;
    @FXML private Button btnSave;
    @FXML private Button btnRemove;

    @FXML
    public void initialize() {
        // This runs automatically when tenant_dashboard.fxml loads
        loadTenantData();
    }

    private void loadTenantData() {
        // ✅ STRUCTURAL FACADE & SERIALIZATION: Load active user from DormitoryFacade
        User sessionUser = DormitoryFacade.getInstance().getActiveUser();
        if (sessionUser != null) {
            if (usernameLabel != null) usernameLabel.setText(sessionUser.getUsername());
            if (roomLabel != null) roomLabel.setText(
                sessionUser.getRoomNumber() != null ? sessionUser.getRoomNumber() : "N/A");
            if (statusLabel != null) statusLabel.setText(
                sessionUser.getLeaseStatus() != null ? sessionUser.getLeaseStatus() : "Pending");
        } else {
            // No session found — redirect back to login
            System.out.println("[TenantDashboard] No session file found. Redirecting to login.");
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        // ✅ STRUCTURAL FACADE & SERIALIZATION: Terminate session via DormitoryFacade
        DormitoryFacade.getInstance().logout();
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
    }

    // --- Empty Stub Handlers for Option B (Prevents tenant.fxml from crashing) ---
    @FXML private void handleNavDashboard(ActionEvent event) {}
    @FXML private void handleNavTenants(ActionEvent event) {}
    @FXML private void handleNavRooms(ActionEvent event) {}
    @FXML private void handleNavBilling(ActionEvent event) {}
    @FXML private void handleNavMaintenance(ActionEvent event) {}
    @FXML private void handleSignOut(ActionEvent event) {
        DormitoryFacade.getInstance().logout();
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
    }
    @FXML private void handleSaveTenant(ActionEvent event) {}
    @FXML private void handleRemoveTenant(ActionEvent event) {}
}