package com.example.project;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GameOverHandler {

    public static void gameOver(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("پایان بازی");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
            System.exit(0); // خروج از برنامه
        });
    }
}
