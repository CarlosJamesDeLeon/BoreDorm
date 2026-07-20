package com.boredom.boredorm.BillingControl;

import com.boredom.boredorm.NavigationUtil;
import com.boredom.boredorm.SessionManaging.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class BillingController implements Initializable {
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
    @FXML private ComboBox<String> comboFilterStatus;
    @FXML private TableView<Object> invoiceTable;
    @FXML private TableColumn<Object, String> colTenant;
    @FXML private TableColumn<Object, String> colRoom;
    @FXML private TableColumn<Object, String> colPeriod;
    @FXML private TableColumn<Object, String> colAmount;
    @FXML private TableColumn<Object, String> colStatus;
    @FXML private VBox rightPanel;
    @FXML private Label detailsHeader;
    @FXML private Label lblAmount;
    @FXML private TextField editAmount;
    @FXML private Label lblMethod;
    @FXML private ComboBox<String> comboMethod;
    @FXML private Label lblDate;
    @FXML private TextField editDate;
    @FXML private Button btnRecord;
    @FXML private Label postStatusLabel;

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
        comboFilterStatus.setId("comboFilterStatus");
        invoiceTable.setId("invoiceTable");
        colTenant.setId("colTenant");
        colRoom.setId("colRoom");
        colPeriod.setId("colPeriod");
        colAmount.setId("colAmount");
        colStatus.setId("colStatus");
        rightPanel.setId("rightPanel");
        detailsHeader.setId("detailsHeader");
        lblAmount.setId("lblAmount");
        editAmount.setId("editAmount");
        lblMethod.setId("lblMethod");
        comboMethod.setId("comboMethod");
        lblDate.setId("lblDate");
        editDate.setId("editDate");
        btnRecord.setId("btnRecord");
        postStatusLabel.setId("postStatusLabel");
        comboFilterStatus.getItems().addAll("Pending", "Paid", "Overdue");
        comboMethod.getItems().addAll("Cash", "Bank Transfer", "GCash", "Credit Card");
    }

    @FXML
    private void handleRecordPayment(ActionEvent event) {
        String amount = editAmount.getText();
        String method = comboMethod.getValue();
        if (amount == null || amount.trim().isEmpty() || method == null) {
            postStatusLabel.setText("Please fill required fields.");
            postStatusLabel.setVisible(true);
            return;
        }
        postStatusLabel.setText("Payment of " + amount + " posted via " + method);
        postStatusLabel.setVisible(true);
        editAmount.clear();
        editDate.clear();
        comboMethod.setValue(null);
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
        System.out.println("[Billing] Session file deleted. User logged out.");
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
    }
}