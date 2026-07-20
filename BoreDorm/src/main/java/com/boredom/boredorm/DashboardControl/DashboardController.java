package com.boredom.boredorm.DashboardControl;

import com.boredom.boredorm.DAO.UserDAO;
import com.boredom.boredorm.DAO.UserDAOImpl;
import com.boredom.boredorm.Models.TenantProfile;
import com.boredom.boredorm.Models.ActivityLog; // Make sure to create this model class!
import com.boredom.boredorm.NavigationUtil;
import com.boredom.boredorm.SessionManaging.SessionManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML private Label lblOccupiedNum;
    @FXML private Label lblVacantNum;
    @FXML private Label lblPendingNum;
    @FXML private Label lblRequestsNum;

    // Updated to use the ActivityLog model
    @FXML private TableView<ActivityLog> activityTable;
    @FXML private TableColumn<ActivityLog, String> colTenant;
    @FXML private TableColumn<ActivityLog, String> colAction;
    @FXML private TableColumn<ActivityLog, String> colTimestamp;

    @FXML private Button navTenants;
    @FXML private Button navRooms;
    @FXML private Button navBilling;
    @FXML private Button btnSignOut;

    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Link columns to the exact field property names inside ActivityLog.java
        colTenant.setCellValueFactory(new PropertyValueFactory<>("tenantName"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("actionLogged"));
        colTimestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        // Let the OS frame window render natively based on screen bounds
        Platform.runLater(() -> {
            if (lblOccupiedNum.getScene() != null) {
                javafx.stage.Stage currentStage = (javafx.stage.Stage) lblOccupiedNum.getScene().getWindow();
                currentStage.setResizable(true);
                currentStage.sizeToScene();
                currentStage.centerOnScreen();
            }
        });

        loadDashboardData();
        applyRoleBasedAccess();
    }

    private void applyRoleBasedAccess() {
        String role = SessionManager.getCurrentUserRole();

        if (role != null && !"admin".equalsIgnoreCase(role.trim())) {
            if (navTenants != null) navTenants.setVisible(false);
            if (navRooms != null) navRooms.setVisible(false);
            if (navBilling != null) navBilling.setVisible(false);
        }
    }

    private void loadDashboardData() {
        ObservableList<ActivityLog> logList = FXCollections.observableArrayList();

        try {
            // Fetch real profiles from your database
            List<TenantProfile> profiles = userDAO.getAllTenantProfiles();

            if (profiles != null && !profiles.isEmpty()) {
                // Map database profiles dynamically to table rows
                for (TenantProfile profile : profiles) {
                    logList.add(new ActivityLog(
                            profile.getUsername(),
                            "Logged into the system hub",
                            "2026-07-18 20:30"
                    ));
                }
            } else {
                // Fallback sample data if database table is currently empty
                logList.add(new ActivityLog("John Doe", "Checked into Room 102", "2026-07-18 14:22"));
                logList.add(new ActivityLog("Jane Smith", "Submitted Maintenance Request #104", "2026-07-18 15:05"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Fallback backup if the DB implementation throws a connection error
            logList.add(new ActivityLog("System Error", "Failed to retrieve real-time log data", "Now"));
        }

        // Populate the UI component
        activityTable.setItems(logList);

        // Populate upper summary metrics cards
        lblOccupiedNum.setText("42");
        lblVacantNum.setText("18");
        lblPendingNum.setText("0");
        lblRequestsNum.setText("5");
    }

    @FXML private void handleNavDashboard(ActionEvent event) {}

    @FXML private void handleNavTenants(ActionEvent event) {
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/tenant.fxml");
    }

    @FXML private void handleNavRooms(ActionEvent event) {
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/room.fxml");
    }

    @FXML private void handleNavBilling(ActionEvent event) {
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/billing.fxml");
    }

    @FXML private void handleNavMaintenance(ActionEvent event) {
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/maintenanceRequests.fxml");
    }

    @FXML private void handleSignOut(ActionEvent event) {
        // ✅ SERIALIZATION: Deletes session.dat file on logout
        SessionManager.clearSession();
        System.out.println("[Dashboard] Session file deleted. User logged out.");
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
    }
}