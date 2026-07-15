package com.boredom.boredorm.MaintenanceControl;

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
        comboFilterStatus.getItems().addAll("New", "In Progress", "Resolved");
    }

    @FXML private void handleNavDashboard(ActionEvent event) { com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/dashboard.fxml"); }
    @FXML private void handleNavTenants(ActionEvent event) { com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/tenant.fxml"); }
    @FXML private void handleNavRooms(ActionEvent event) { com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/room.fxml"); }
    @FXML private void handleNavBilling(ActionEvent event) { com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/billing.fxml"); }
    @FXML private void handleNavMaintenance(ActionEvent event) { com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/maintenanceRequests.fxml"); }
    @FXML private void handleSignOut(ActionEvent event) { com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml"); }
}
