package com.boredom.boredorm;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.net.URL;

public class NavigationUtil {

    public static void navigateTo(ActionEvent event, String fxmlPath) {
        try {
            URL resource = NavigationUtil.class.getResource(fxmlPath);
            if (resource == null) {
                System.err.println("CRITICAL: FXML file not found: " + fxmlPath);
                return;
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            // Grab the current active scene window
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Swap the internal root layout container seamlessly without changing window sizes
            currentScene.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}