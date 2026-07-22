package com.boredom.boredorm.TenantDashboardControl;

import com.boredom.boredorm.Facade.DormitoryFacade;
import com.boredom.boredorm.Models.MaintenanceRequest;
import com.boredom.boredorm.Models.User;
import com.boredom.boredorm.NavigationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;

import java.util.List;

/**
 * Controller for the Tenant Dashboard (tenant_dashboard.fxml).
 * Allows tenants to:
 *  1. View their profile info (room, username, lease status)
 *  2. Submit maintenance requests
 *  3. View their own submitted maintenance requests (status reflects admin updates)
 */
public class TenantDashboardController {

    // --- Profile Info Labels ---
    @FXML private Label roomLabel;
    @FXML private Label usernameLabel;
    @FXML private Label statusLabel;
    @FXML private Button signOutButton;

    // --- Maintenance Request Form ---
    @FXML private ComboBox<String> comboIssueType;
    @FXML private TextArea txtDescription;
    @FXML private Button btnSubmitRequest;
    @FXML private Label lblSubmitStatus;

    // --- My Requests Table ---
    @FXML private TableView<MaintenanceRequest> myRequestsTable;
    @FXML private TableColumn<MaintenanceRequest, String> colIssueType;
    @FXML private TableColumn<MaintenanceRequest, String> colDescription;
    @FXML private TableColumn<MaintenanceRequest, String> colDateFiled;
    @FXML private TableColumn<MaintenanceRequest, String> colStatus;
    @FXML private TableColumn<MaintenanceRequest, String> colAssigned;

    @FXML
    public void initialize() {
        // Wire TableView columns to MaintenanceRequest properties
        colIssueType.setCellValueFactory(new PropertyValueFactory<>("issueType"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDateFiled.setCellValueFactory(new PropertyValueFactory<>("dateFiled"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colAssigned.setCellValueFactory(new PropertyValueFactory<>("assignedStaff"));

        // Populate issue type dropdown
        comboIssueType.getItems().addAll(
                "Plumbing", "Electrical", "AC / Ventilation", "Pest Control",
                "Furniture Damage", "Cleaning", "Security / Door", "Other"
        );

        // Load profile and existing requests
        loadProfileData();
        loadMyRequests();
    }

    private void loadProfileData() {
        User sessionUser = DormitoryFacade.getInstance().getActiveUser();
        if (sessionUser != null) {
            if (usernameLabel != null) usernameLabel.setText(sessionUser.getUsername());
            if (roomLabel != null) roomLabel.setText(
                sessionUser.getRoomNumber() != null ? sessionUser.getRoomNumber() : "N/A");
            if (statusLabel != null) statusLabel.setText(
                sessionUser.getLeaseStatus() != null ? sessionUser.getLeaseStatus() : "Pending");
        } else {
            System.out.println("[TenantDashboard] No session file found.");
        }
    }

    private void loadMyRequests() {
        List<MaintenanceRequest> requests = DormitoryFacade.getInstance().getMyMaintenanceRequests();
        ObservableList<MaintenanceRequest> data = FXCollections.observableArrayList(requests);
        myRequestsTable.setItems(data);
    }

    @FXML
    private void handleSubmitRequest(ActionEvent event) {
        String issueType = comboIssueType.getValue();
        String description = txtDescription.getText();

        if (issueType == null || issueType.trim().isEmpty()) {
            lblSubmitStatus.setText("Please select an issue type.");
            lblSubmitStatus.setStyle("-fx-text-fill: #e74c3c;");
            lblSubmitStatus.setVisible(true);
            return;
        }
        if (description == null || description.trim().isEmpty()) {
            lblSubmitStatus.setText("Please describe the issue.");
            lblSubmitStatus.setStyle("-fx-text-fill: #e74c3c;");
            lblSubmitStatus.setVisible(true);
            return;
        }

        User user = DormitoryFacade.getInstance().getActiveUser();
        if (user == null) {
            lblSubmitStatus.setText("Session expired. Please log in again.");
            lblSubmitStatus.setStyle("-fx-text-fill: #e74c3c;");
            lblSubmitStatus.setVisible(true);
            return;
        }

        MaintenanceRequest request = new MaintenanceRequest();
        request.setTenantUserId(user.getUserId());
        request.setTenantUsername(user.getUsername());
        request.setRoomNumber(user.getRoomNumber() != null ? user.getRoomNumber() : "N/A");
        request.setIssueType(issueType);
        request.setDescription(description.trim());

        boolean success = DormitoryFacade.getInstance().submitMaintenanceRequest(request);

        if (success) {
            lblSubmitStatus.setText("✅ Request submitted successfully! Admin has been notified.");
            lblSubmitStatus.setStyle("-fx-text-fill: #27ae60;");
            comboIssueType.setValue(null);
            txtDescription.clear();
            loadMyRequests(); // Refresh table
        } else {
            lblSubmitStatus.setText("❌ Failed to submit request. Please try again.");
            lblSubmitStatus.setStyle("-fx-text-fill: #e74c3c;");
        }
        lblSubmitStatus.setVisible(true);
    }

    @FXML
    private void handleRefreshRequests(ActionEvent event) {
        loadMyRequests();
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        DormitoryFacade.getInstance().logout();
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
    }

    // Stub handlers to satisfy any older FXML references
    @FXML private void handleSignOut(ActionEvent event) {
        DormitoryFacade.getInstance().logout();
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
    }
}