module com.boredom.boredorm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires jbcrypt;

    opens com.boredom.boredorm to javafx.fxml, javafx.graphics;
    opens com.boredom.boredorm.LoginControl to javafx.fxml;
    opens com.boredom.boredorm.DashboardControl to javafx.fxml;
    opens com.boredom.boredorm.BillingControl to javafx.fxml;
    opens com.boredom.boredorm.MaintenanceControl to javafx.fxml;
    opens com.boredom.boredorm.RoomControl to javafx.fxml;
    opens com.boredom.boredorm.TenantControl to javafx.fxml;


    opens com.boredom.boredorm.RegisterControl to javafx.fxml;

    opens com.boredom.boredorm.SessionManaging to javafx.fxml;
    exports com.boredom.boredorm.SessionManaging;
    exports com.boredom.boredorm;
    exports com.boredom.boredorm.RegisterControl;
    opens com.boredom.boredorm.TenantDashboardControl to javafx.fxml;
    exports com.boredom.boredorm.TenantControl;


}