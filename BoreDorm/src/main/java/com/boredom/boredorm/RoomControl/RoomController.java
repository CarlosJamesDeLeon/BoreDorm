package com.boredom.boredorm.RoomControl;


import com.boredom.boredorm.NavigationUtil;
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
        sidebar.setId("sidebar");
        sidebarTitle.setId("sidebarTitle");
        navDashboard.setId("navDashboard");
        navTenants.setId("navTenants");
        navRooms.setId("navRooms");
        navBilling.setId("navBilling");
        navMaintenance.setId("navMaintenance");
        btnSignOut.setId("btnSignOut");
        splitPane.setId("splitPane");
        leftScrollPane.setId("leftScrollPane");
        roomsContainer.setId("roomsContainer");
        pageTitle.setId("pageTitle");
        floor1Container.setId("floor1Container");
        floor1Label.setId("floor1Label");
        floor1Grid.setId("floor1Grid");
        floor2Container.setId("floor2Container");
        floor2Label.setId("floor2Label");
        floor2Grid.setId("floor2Grid");
        rightPanel.setId("rightPanel");
        detailsHeader.setId("detailsHeader");
        detailsRoomTag.setId("detailsRoomTag");
        detailsStatus.setId("detailsStatus");
        lblRoomType.setId("lblRoomType");
        editType.setId("editType");
        lblCapacity.setId("lblCapacity");
        editCapacity.setId("editCapacity");
        lblRate.setId("lblRate");
        editRate.setId("editRate");
        lblRoomStatus.setId("lblRoomStatus");
        comboStatus.setId("comboStatus");
        btnSave.setId("btnSave");
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
        if (stat != null) {
            detailsStatus.setText(stat);
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
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
    }
}