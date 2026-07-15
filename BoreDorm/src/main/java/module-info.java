module com.boredom.boredorm {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.boredom.boredorm to javafx.fxml, javafx.graphics;
    opens com.boredom.boredorm.LoginControl to javafx.fxml;
    opens com.boredom.boredorm.DashboardControl to javafx.fxml;
    opens com.boredom.boredorm.BillingControl to javafx.fxml;
    opens com.boredom.boredorm.MaintenanceControl to javafx.fxml;
    opens com.boredom.boredorm.RoomControl to javafx.fxml;
    opens com.boredom.boredorm.TenantControl to javafx.fxml;

    exports com.boredom.boredorm;
}