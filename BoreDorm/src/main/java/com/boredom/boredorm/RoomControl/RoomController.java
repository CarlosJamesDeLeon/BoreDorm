package com.boredom.boredorm.RoomControl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class RoomController implements Initializable {
    @FXML private VBox sidebar;
    @FXML private Label sidebarTitle;
    @FXML private Button navDashboard;
    @FXML private Button navTenants;
    @FXML private Button navRooms;
    @FXML private Button navBilling;
    @FXML private Button navMaintenance;
    @FXML private Button btnSignOut;
    @FXML private HBox splitPane;
    @FXML private ScrollPane leftScrollPane;
    @FXML private VBox roomsContainer;
    @FXML private Label pageTitle;
    @FXML private VBox floor1Container;
    @FXML private Label floor1Label;
    @FXML private FlowPane floor1Grid;
    @FXML private VBox floor2Container;
    @FXML private Label floor2Label;
    @FXML private FlowPane floor2Grid;
    @FXML private VBox rightPanel;
    @FXML private Label detailsHeader;
    @FXML private Label detailsRoomTag;
    @FXML private Label detailsStatus;
    @FXML private Label lblRoomType;
    @FXML private TextField editType;
    @FXML private Label lblCapacity;
    @FXML private TextField editCapacity;
    @FXML private Label lblRate;
    @FXML private TextField editRate;
    @FXML private Label lblRoomStatus;
    @FXML private ComboBox<String> comboStatus;
    @FXML private Button btnSave;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboStatus.getItems().addAll("Occupied", "Vacant", "Under Maintenance");
        populateFloorGrids();
    }

    private void populateFloorGrids() {
        floor1Grid.getChildren().clear();
        floor2Grid.getChildren().clear();
        for (int i = 101; i <= 108; i++) {
            Button btn = new Button("R" + i);
            btn.getStyleClass().add("room-tag-btn");
            if (i % 3 == 0) {
                btn.getStyleClass().add("room-tag-vacant");
            } else {
                btn.getStyleClass().add("room-tag-occupied");
            }
            floor1Grid.getChildren().add(btn);
        }
        for (int i = 201; i <= 208; i++) {
            Button btn = new Button("R" + i);
            btn.getStyleClass().add("room-tag-btn");
            if (i % 4 == 0) {
                btn.getStyleClass().add("room-tag-maintenance");
            } else if (i % 2 == 0) {
                btn.getStyleClass().add("room-tag-vacant");
            } else {
                btn.getStyleClass().add("room-tag-occupied");
            }
            floor2Grid.getChildren().add(btn);
        }
    }

    @FXML
    private void handleSaveRoom(ActionEvent event) {
        String stat = comboStatus.getValue();
        if (stat != null) detailsStatus.setText(stat);
    }

    @FXML private void handleNavDashboard(ActionEvent event) { com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/dashboard.fxml"); }
    @FXML private void handleNavTenants(ActionEvent event) { com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/tenant.fxml"); }
    @FXML private void handleNavRooms(ActionEvent event) { com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/room.fxml"); }
    @FXML private void handleNavBilling(ActionEvent event) { com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/billing.fxml"); }
    @FXML private void handleNavMaintenance(ActionEvent event) { com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/maintenanceRequests.fxml"); }
    @FXML private void handleSignOut(ActionEvent event) { com.boredom.boredorm.NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml"); }
}
