package com.boredom.boredorm.RoomControl;

import com.boredom.boredorm.NavigationUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import com.boredom.boredorm.Facade.DormitoryFacade;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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

    // Counter Labels
    @FXML private Label lblOccupiedCount;
    @FXML private Label lblVacantCount;
    @FXML private Label lblMaintenanceCount;

    // Side Profile form mappings
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

    // Temporary storage holding state until RoomDAO is ready
    private final Map<String, DormRoom> roomDatabase = new HashMap<>();
    private String selectedRoomId = "R101";

    // Lightweight inner class representing a single Room entity
    private static class DormRoom {
        String id;
        String type;
        String capacity;
        String rate;
        String status;

        DormRoom(String id, String type, String capacity, String rate, String status) {
            this.id = id;
            this.type = type;
            this.capacity = capacity;
            this.rate = rate;
            this.status = status;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ID style injection bindings
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

        // Initialize mock room repository data values matching generation conditions
        initializeMockDatabase();

        // Render buttons and assign click behaviors
        populateFloorGrids();

        // Show initial room (R101) data context
        selectRoom(selectedRoomId);

        // Calculate counter metrics
        updateCounters();
    }

    private void initializeMockDatabase() {
        for (int i = 101; i <= 108; i++) {
            String id = "R" + i;
            String status = (i % 3 == 0) ? "Vacant" : "Occupied";
            roomDatabase.put(id, new DormRoom(id, "Standard Studio", "2", "5000", status));
        }
        for (int i = 201; i <= 208; i++) {
            String id = "R" + i;
            String status = "Occupied";
            if (i % 4 == 0) {
                status = "Under Maintenance";
            } else if (i % 2 == 0) {
                status = "Vacant";
            }
            roomDatabase.put(id, new DormRoom(id, "Deluxe Suite", "4", "8500", status));
        }
    }

    private void populateFloorGrids() {
        floor1Grid.getChildren().clear();
        floor2Grid.getChildren().clear();

        for (int i = 101; i <= 108; i++) {
            String id = "R" + i;
            Button btn = new Button(id);
            btn.getStyleClass().add("room-tag-btn");
            btn.setOnAction(e -> selectRoom(id));
            floor1Grid.getChildren().add(btn);
        }

        for (int i = 201; i <= 208; i++) {
            String id = "R" + i;
            Button btn = new Button(id);
            btn.getStyleClass().add("room-tag-btn");
            btn.setOnAction(e -> selectRoom(id));
            floor2Grid.getChildren().add(btn);
        }
    }

    private void selectRoom(String roomId) {
        selectedRoomId = roomId;
        DormRoom room = roomDatabase.get(roomId);

        if (room != null) {
            detailsRoomTag.setText(room.id);
            detailsStatus.setText(room.status);
            editType.setText(room.type);
            editCapacity.setText(room.capacity);
            editRate.setText(room.rate);
            comboStatus.setValue(room.status);
        }
    }

    private void updateCounters() {
        int occupied = 0;
        int vacant = 0;
        int maintenance = 0;

        for (DormRoom room : roomDatabase.values()) {
            switch (room.status) {
                case "Occupied" -> occupied++;
                case "Vacant" -> vacant++;
                case "Under Maintenance" -> maintenance++;
            }
        }

        lblOccupiedCount.setText(String.valueOf(occupied));
        lblVacantCount.setText(String.valueOf(vacant));
        lblMaintenanceCount.setText(String.valueOf(maintenance));
    }

    @FXML
    private void handleSaveRoom(ActionEvent event) {
        DormRoom room = roomDatabase.get(selectedRoomId);
        if (room != null) {
            room.type = editType.getText();
            room.capacity = editCapacity.getText();
            room.rate = editRate.getText();

            String stat = comboStatus.getValue();
            if (stat != null) {
                room.status = stat;
                detailsStatus.setText(stat);
            }

            // Refresh counts across top metrics bar instantly
            updateCounters();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Room configuration updated successfully!", ButtonType.OK);
            alert.showAndWait();
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
        DormitoryFacade.getInstance().logout();
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
    }
}