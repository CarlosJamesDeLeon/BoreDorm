package com.boredom.boredorm.MaintenanceControl;

import com.boredom.boredorm.NavigationUtil;
import com.boredom.boredorm.SessionManaging.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

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
    @FXML private TableView<Object> maintenanceTable;
    @FXML private TableColumn<Object, String> colTenant;
    @FXML private TableColumn<Object, String> colRoom;
    @FXML private TableColumn<Object, String> colDescription;
    @FXML private TableColumn<Object, String> colDateFiled;
    @FXML private TableColumn<Object, String> colStatus;
    @FXML private TableColumn<Object, String> colStaff;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sidebar.setId("sidebar");
        sidebarTitle.setId("sidebarTitle");
        navDashboard.setId("navDashboard");
        navTenants.setId("navTenants");
        navRooms.setId("navRooms");
        navBilling.setId("navBilling");
        navMaintenance.setId("navMaintenance");
        btnSignOut.setId("btnSignOut");
        contentArea.setId("contentArea");
        pageTitle.setId("pageTitle");
        searchField.setId("searchField");
        comboFilterStatus.setId("comboFilterStatus");
        maintenanceTable.setId("maintenanceTable");
        colTenant.setId("colTenant");
        colRoom.setId("colRoom");
        colDescription.setId("colDescription");
        colDateFiled.setId("colDateFiled");
        colStatus.setId("colStatus");
        colStaff.setId("colStaff");
        comboFilterStatus.getItems().addAll("New", "In Progress", "Resolved");
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
        // ✅ SERIALIZATION: Delete session.dat on sign out
        SessionManager.clearSession();
        System.out.println("[Maintenance] Session file deleted. User logged out.");
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
    }
}