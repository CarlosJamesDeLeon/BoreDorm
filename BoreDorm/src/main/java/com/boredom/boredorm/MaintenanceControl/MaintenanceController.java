package com.boredom.boredorm.MaintenanceControl;

import com.boredom.boredorm.Facade.DormitoryFacade;
import com.boredom.boredorm.Models.MaintenanceRequest;
import com.boredom.boredorm.NavigationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Admin-side Maintenance Requests Controller.
 * Loads all tenant-submitted maintenance requests from the database,
 * allows filtering, and lets admin update request status.
 */
public class MaintenanceController implements Initializable {

    @FXML private VBox sidebar;
    @FXML private Label sidebarTitle;
    @FXML private Button navDashboard;
    @FXML private Button navTenants;
    @FXML private Button navRooms;
    @FXML private Button navBilling;
    @FXML private Button navMaintenance;
    @FXML private Button btnSignOut;
    @FXML private VBox contentArea;
    @FXML private Label pageTitle;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> comboFilterStatus;

    @FXML private TableView<MaintenanceRequest> maintenanceTable;
    @FXML private TableColumn<MaintenanceRequest, String> colTenant;
    @FXML private TableColumn<MaintenanceRequest, String> colRoom;
    @FXML private TableColumn<MaintenanceRequest, String> colDescription;
    @FXML private TableColumn<MaintenanceRequest, String> colDateFiled;
    @FXML private TableColumn<MaintenanceRequest, String> colStatus;
    @FXML private TableColumn<MaintenanceRequest, String> colStaff;

    // Detail / update panel
    @FXML private Label lblSelectedTenant;
    @FXML private Label lblSelectedRoom;
    @FXML private Label lblSelectedIssue;
    @FXML private Label lblSelectedDesc;
    @FXML private ComboBox<String> comboUpdateStatus;
    @FXML private TextField editAssignedStaff;
    @FXML private Button btnUpdateStatus;
    @FXML private Label lblUpdateResult;

    private ObservableList<MaintenanceRequest> allRequests = FXCollections.observableArrayList();
    private MaintenanceRequest selectedRequest;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Wire columns to MaintenanceRequest properties
        colTenant.setCellValueFactory(new PropertyValueFactory<>("tenantUsername"));
        colRoom.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDateFiled.setCellValueFactory(new PropertyValueFactory<>("dateFiled"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStaff.setCellValueFactory(new PropertyValueFactory<>("assignedStaff"));

        // Status filter combo
        comboFilterStatus.getItems().addAll("All", "Pending", "In Progress", "Resolved");
        comboFilterStatus.setValue("All");
        comboUpdateStatus.getItems().addAll("Pending", "In Progress", "Resolved");

        // Filter listener
        comboFilterStatus.setOnAction(e -> applyFilter());
        searchField.textProperty().addListener((obs, oldVal, newVal) -> applyFilter());

        // Row selection → fill detail panel
        maintenanceTable.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldSel, newSel) -> {
                if (newSel != null) {
                    selectedRequest = newSel;
                    if (lblSelectedTenant != null) lblSelectedTenant.setText(newSel.getTenantUsername());
                    if (lblSelectedRoom != null)   lblSelectedRoom.setText(newSel.getRoomNumber());
                    if (lblSelectedIssue != null)  lblSelectedIssue.setText(newSel.getIssueType());
                    if (lblSelectedDesc != null)   lblSelectedDesc.setText(newSel.getDescription());
                    if (comboUpdateStatus != null) comboUpdateStatus.setValue(newSel.getStatus());
                    if (editAssignedStaff != null) editAssignedStaff.setText(
                        "Unassigned".equals(newSel.getAssignedStaff()) ? "" : newSel.getAssignedStaff());
                }
            }
        );

        loadRequests();
    }

    private void loadRequests() {
        List<MaintenanceRequest> requests = DormitoryFacade.getInstance().getAllMaintenanceRequests();
        allRequests.setAll(requests);
        maintenanceTable.setItems(allRequests);
    }

    private void applyFilter() {
        String statusFilter = comboFilterStatus.getValue();
        String search = searchField.getText() == null ? "" : searchField.getText().toLowerCase();

        List<MaintenanceRequest> filtered = allRequests.stream()
            .filter(r -> {
                boolean statusMatch = "All".equals(statusFilter) || r.getStatus().equals(statusFilter);
                boolean searchMatch = search.isEmpty()
                    || r.getTenantUsername().toLowerCase().contains(search)
                    || r.getDescription().toLowerCase().contains(search)
                    || r.getRoomNumber().toLowerCase().contains(search);
                return statusMatch && searchMatch;
            })
            .collect(Collectors.toList());

        maintenanceTable.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void handleUpdateStatus(ActionEvent event) {
        if (selectedRequest == null) {
            if (lblUpdateResult != null) {
                lblUpdateResult.setText("Please select a request first.");
                lblUpdateResult.setStyle("-fx-text-fill: #e74c3c;");
                lblUpdateResult.setVisible(true);
            }
            return;
        }

        String newStatus = comboUpdateStatus.getValue();
        String staff = editAssignedStaff != null ? editAssignedStaff.getText().trim() : "Unassigned";
        if (staff.isEmpty()) staff = "Unassigned";

        boolean success = DormitoryFacade.getInstance()
            .updateMaintenanceStatus(selectedRequest.getRequestId(), newStatus, staff);

        if (success) {
            if (lblUpdateResult != null) {
                lblUpdateResult.setText("✅ Status updated to \"" + newStatus + "\" successfully.");
                lblUpdateResult.setStyle("-fx-text-fill: #27ae60;");
                lblUpdateResult.setVisible(true);
            }
            loadRequests();
        } else {
            if (lblUpdateResult != null) {
                lblUpdateResult.setText("❌ Failed to update status.");
                lblUpdateResult.setStyle("-fx-text-fill: #e74c3c;");
                lblUpdateResult.setVisible(true);
            }
        }
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        loadRequests();
    }

    @FXML
    private void handleNavDashboard(ActionEvent event) {
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/dashboard.fxml");
    }

    @FXML
    private void handleNavTenants(ActionEvent event) {
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/tenant.fxml");
    }

    @FXML
    private void handleNavRooms(ActionEvent event) {
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/room.fxml");
    }

    @FXML
    private void handleNavBilling(ActionEvent event) {
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/billing.fxml");
    }

    @FXML
    private void handleNavMaintenance(ActionEvent event) {
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/maintenanceRequests.fxml");
    }

    @FXML
    private void handleSignOut(ActionEvent event) {
        DormitoryFacade.getInstance().logout();
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
    }
}