package com.boredom.boredorm.TenantControl;

import com.boredom.boredorm.NavigationUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class TenantController implements Initializable {
    @FXML private VBox sidebar;
    @FXML private Label sidebarTitle;
    @FXML private Button navDashboard;
    @FXML private Button navTenants;
    @FXML private Button navRooms;
    @FXML private Button navBilling;
    @FXML private Button navMaintenance;
    @FXML private Button btnSignOut;
    @FXML private HBox splitPane;
    @FXML private VBox leftPanel;
    @FXML private Label panelTitle;
    @FXML private TextField searchField;
    @FXML private TableView<Object> tenantTable;
    @FXML private TableColumn<Object, String> colRoom;
    @FXML private TableColumn<Object, String> colName;
    @FXML private TableColumn<Object, String> colStatus;
    @FXML private VBox rightPanel;
    @FXML private Label detailsHeader;
    @FXML private Label profileRoomTag;
    @FXML private Label profileName;
    @FXML private Label lblFullName;
    @FXML private TextField editName;
    @FXML private Label lblContact;
    @FXML private TextField editContact;
    @FXML private Label lblMoveIn;
    @FXML private TextField editMoveIn;
    @FXML private Label lblLeaseStatus;
    @FXML private ComboBox<String> comboLeaseStatus;
    @FXML private Button btnSave;
    @FXML private Button btnRemove;

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
        leftPanel.setId("leftPanel");
        panelTitle.setId("panelTitle");
        searchField.setId("searchField");
        tenantTable.setId("tenantTable");
        colRoom.setId("colRoom");
        colName.setId("colName");
        colStatus.setId("colStatus");
        rightPanel.setId("rightPanel");
        detailsHeader.setId("detailsHeader");
        profileRoomTag.setId("profileRoomTag");
        profileName.setId("profileName");
        lblFullName.setId("lblFullName");
        editName.setId("editName");
        lblContact.setId("lblContact");
        editContact.setId("editContact");
        lblMoveIn.setId("lblMoveIn");
        editMoveIn.setId("editMoveIn");
        lblLeaseStatus.setId("lblLeaseStatus");
        comboLeaseStatus.setId("comboLeaseStatus");
        btnSave.setId("btnSave");
        btnRemove.setId("btnRemove");
        comboLeaseStatus.getItems().addAll("Active", "Overdue", "Under Notice", "Expired");
    }

    @FXML
    private void handleSaveTenant(ActionEvent event) {
        String name = editName.getText();
        if (name == null || name.trim().isEmpty()) {
            return;
        }
        profileName.setText(name);
    }

    @FXML
    private void handleRemoveTenant(ActionEvent event) {
        editName.clear();
        editContact.clear();
        editMoveIn.clear();
        comboLeaseStatus.setValue(null);
        profileName.setText("Select a Tenant");
        profileRoomTag.setText("-");
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