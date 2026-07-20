package com.boredom.boredorm;

import com.boredom.boredorm.Models.User;
import com.boredom.boredorm.SessionManaging.SessionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String initialFxml = "/com/boredom/boredorm/login.fxml";

        // ✅ SERIALIZATION: Use the serialized session file to maintain state on launch
        if (SessionManager.isSessionActive()) {
            User user = SessionManager.loadSession();
            if (user != null) {
                if ("Admin".equalsIgnoreCase(user.getRole())) {
                    initialFxml = "/com/boredom/boredorm/dashboard.fxml";
                } else {
                    initialFxml = "/com/boredom/boredorm/tenant_dashboard.fxml";
                }
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(initialFxml));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("BoreDorm Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}