package com.boredom.boredorm.DashboardControl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
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
    @FXML private VBox cardOccupied;
    @FXML private Label lblOccupiedNum;
    @FXML private Label lblOccupiedTitle;
    @FXML private VBox cardVacant;
    @FXML private Label lblVacantNum;
    @FXML private Label lblVacantTitle;
    @FXML private VBox cardPending;
    @FXML private Label lblPendingNum;
    @FXML private Label lblPendingTitle;
    @FXML private VBox cardRequests;
    @FXML private Label lblRequestsNum;
    @FXML private Label lblRequestsTitle;
    @FXML private Label gridTitle;
    @FXML private FlowPane roomGrid;
    @FXML private Label activityTitle;
    @FXML private TableView<Object> activityTable;
    @FXML private TableColumn<Object, String> colTenant;
    @FXML private TableColumn<Object, String> colAction;
    @FXML private TableColumn<Object, String> colTimestamp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadOverviewStats();
        populateRoomGrid();
    }

    private void loadOverviewStats() {
        lblOccupiedNum.setText("42");
        lblVacantNum.setText("18");
        lblPendingNum.setText("$1,240");
        lblRequestsNum.setText("5");
    }

    private void populateRoomGrid() {
        roomGrid.getChildren().clear();
        for (int i = 101; i <= 112; i++) {
            Button roomBtn = new Button("R" + i);
            roomBtn.getStyleClass().add("room-tag-btn");
            if (i % 3 == 0) {
                roomBtn.getStyleClass().add("room-tag-vacant");
            } else if (i % 5 == 0) {
                roomBtn.getStyleClass().add("room-tag-maintenance");
            } else {
                roomBtn.getStyleClass().add("room-tag-occupied");
            }
            roomGrid.getChildren().add(roomBtn);
        }
    }

    @FXML private void handleNavDashboard(ActionEvent event) {
        com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/dashboard.fxml");
    }
    @FXML private void handleNavTenants(ActionEvent event) {
        com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/tenant.fxml");
    }
    @FXML private void handleNavRooms(ActionEvent event) {
        com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/room.fxml");
    }
    @FXML private void handleNavBilling(ActionEvent event) {
        com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/billing.fxml");
    }
    @FXML private void handleNavMaintenance(ActionEvent event) {
        com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/maintenanceRequests.fxml");
    }
    @FXML private void handleSignOut(ActionEvent event) {
        com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
    }
}
