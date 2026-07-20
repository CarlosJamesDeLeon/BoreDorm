module com.boredom.boredorm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires jbcrypt;
    requires java.prefs;

    opens com.boredom.boredorm to javafx.fxml, javafx.graphics;
    opens com.boredom.boredorm.LoginControl to javafx.fxml;
    opens com.boredom.boredorm.DashboardControl to javafx.fxml;
    opens com.boredom.boredorm.BillingControl to javafx.fxml;
    opens com.boredom.boredorm.RoomControl to javafx.fxml;

    opens com.boredom.boredorm.Models to javafx.base;
    opens com.boredom.boredorm.TenantControl to javafx.fxml;
    exports com.boredom.boredorm.TenantControl;
    opens com.boredom.boredorm.TenantDashboardControl to javafx.fxml;
    exports com.boredom.boredorm.TenantDashboardControl;

    opens com.boredom.boredorm.MaintenanceControl to javafx.fxml;
    exports com.boredom.boredorm.MaintenanceControl;

    exports com.boredom.boredorm;
}