package com.boredom.boredorm.DashboardControl;

import com.boredom.boredorm.NavigationUtil;
import com.boredom.boredorm.SessionManaging.SessionManager;
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
        cardOccupied.setId("cardOccupied");
        lblOccupiedNum.setId("lblOccupiedNum");
        lblOccupiedTitle.setId("lblOccupiedTitle");
        cardVacant.setId("cardVacant");
        lblVacantNum.setId("lblVacantNum");
        lblVacantTitle.setId("lblVacantTitle");
        cardPending.setId("cardPending");
        lblPendingNum.setId("lblPendingNum");
        lblPendingTitle.setId("lblPendingTitle");
        cardRequests.setId("cardRequests");
        lblRequestsNum.setId("lblRequestsNum");
        lblRequestsTitle.setId("lblRequestsTitle");
        gridTitle.setId("gridTitle");
        roomGrid.setId("roomGrid");
        activityTitle.setId("activityTitle");
        activityTable.setId("activityTable");
        colTenant.setId("colTenant");
        colAction.setId("colAction");
        colTimestamp.setId("colTimestamp");
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
        SessionManager.clearLocalSession();
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
    }
}