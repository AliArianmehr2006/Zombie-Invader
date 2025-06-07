package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class StartPageController {

    @FXML
    private Button stageModeButton;

    @FXML
    private Button recordModeButton;

    @FXML
    private VBox rootVBox;

    @FXML
    private TextField nameField;  // اضافه شد

    @FXML
    public void initialize() {
        try {
            Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/img/background2.jpg"));

            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
            BackgroundImage background = new BackgroundImage(
                    backgroundImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    backgroundSize);
            rootVBox.setBackground(new Background(background));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onStageModeClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project/main_page.fxml"));
            AnchorPane root = loader.load();
            MainPageController controller = loader.getController();
            controller.setRecordMode(false);

            String playerName = nameField.getText().trim();
            if (playerName.isEmpty()) {
                playerName = "Player"; // مقدار پیشفرض اگر چیزی وارد نکرده باشد
            }
            controller.setPlayerName(playerName);

            Stage stage = (Stage) stageModeButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onRecordModeClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project/main_page.fxml"));
            AnchorPane root = loader.load();
            MainPageController controller = loader.getController();
            controller.setRecordMode(true);

            String playerName = nameField.getText().trim();
            if (playerName.isEmpty()) {
                playerName = "Player";
            }
            controller.setPlayerName(playerName);

            Stage stage = (Stage) recordModeButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
