package com.boredom.boredorm.TenantControl;

import com.boredom.boredorm.Facade.DormitoryFacade;
import com.boredom.boredorm.NavigationUtil;
import com.boredom.boredorm.DAO.UserDAO;
import com.boredom.boredorm.DAO.UserDAOImpl;
import com.boredom.boredorm.Models.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.util.List;

public class TenantController {

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

    @FXML private TableView<User> tenantTable;
    @FXML private TableColumn<User, String> colRoom;
    @FXML private TableColumn<User, String> colName;
    @FXML private TableColumn<User, String> colStatus;

    @FXML private VBox rightPanel;
    @FXML private TextField editName;
    @FXML private TextField editContact;
    @FXML private ComboBox<String> comboLeaseStatus;
    @FXML private Label lblFullName;
    @FXML private Label lblContact;
    @FXML private Label lblLeaseStatus;
    @FXML private Button btnSave;
    @FXML private Button btnRemove;

    private final UserDAO userDAO = new UserDAOImpl();
    private final ObservableList<User> tenantList = FXCollections.observableArrayList();
    private User selectedUser = null;

    @FXML
    public void initialize() {
        // 1. Setup ComboBox choices
        if (comboLeaseStatus != null) {
            comboLeaseStatus.setItems(FXCollections.observableArrayList("Active", "Pending", "Terminated"));
        }

        // 2. Map table columns to match User model getter properties perfectly
        colRoom.setCellValueFactory(new PropertyValueFactory<>("roomNumber")); // calls getRoomNumber()
        colName.setCellValueFactory(new PropertyValueFactory<>("username"));   // calls getUsername()
        colStatus.setCellValueFactory(new PropertyValueFactory<>("leaseStatus")); // calls getLeaseStatus()

        // 3. Handle selecting a row to populate the right-side details panel dynamically
        tenantTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedUser = newSelection;
                editName.setText(selectedUser.getUsername());
                comboLeaseStatus.setValue(selectedUser.getLeaseStatus() != null ? selectedUser.getLeaseStatus() : "Active");
            }
        });

        // 4. Pull fresh records from the database
        loadDirectoryData();
    }

    private void loadDirectoryData() {
        try {
            List<User> allUsers = userDAO.getAllUsers();
            tenantList.clear();

            // Filter out Admins so the directory only displays actual Tenants
            for (User user : allUsers) {
                if (!"Admin".equalsIgnoreCase(user.getRole())) {
                    tenantList.add(user);
                }
            }

            // Hook up live searching filter logic
            FilteredList<User> filteredData = new FilteredList<>(tenantList, p -> true);
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(user -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (user.getUsername() != null && user.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            tenantTable.setItems(filteredData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveTenant(ActionEvent event) {
        if (selectedUser != null) {
            try {
                selectedUser.setUsername(editName.getText());
                if (comboLeaseStatus.getValue() != null) {
                    selectedUser.setLeaseStatus(comboLeaseStatus.getValue());
                }

                // TODO: userDAO.updateUser(selectedUser); // Save updates to MySQL when ready

                loadDirectoryData(); // Refresh the table layout
                tenantTable.getSelectionModel().clearSelection();
                clearForm();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleRemoveTenant(ActionEvent event) {
        if (selectedUser != null) {
            try {
                // TODO: userDAO.deleteUser(selectedUser.getUserId());
                loadDirectoryData();
                clearForm();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void clearForm() {
        selectedUser = null;
        editName.clear();
        editContact.clear();
        comboLeaseStatus.setValue(null);
    }

    // --- Working Sidebar Transitions ---
    @FXML private void handleNavDashboard(ActionEvent event) { NavigationUtil.navigateTo(event, "/com/boredom/boredorm/dashboard.fxml"); }
    @FXML private void handleNavTenants(ActionEvent event) {}
    @FXML private void handleNavRooms(ActionEvent event) { NavigationUtil.navigateTo(event, "/com/boredom/boredorm/room.fxml"); }
    @FXML private void handleNavBilling(ActionEvent event) { NavigationUtil.navigateTo(event, "/com/boredom/boredorm/billing.fxml"); }
    @FXML private void handleNavMaintenance(ActionEvent event) { NavigationUtil.navigateTo(event, "/com/boredom/boredorm/maintenanceRequests.fxml"); }
    @FXML private void handleSignOut(ActionEvent event) {
        DormitoryFacade.getInstance().logout();
        NavigationUtil.navigateTo(event, "/com/boredom/boredorm/login.fxml");
    }
}