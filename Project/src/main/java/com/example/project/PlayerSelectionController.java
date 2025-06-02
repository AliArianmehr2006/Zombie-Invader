package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlayerSelectionController {

    @FXML
    private VBox rootVBox;

    @FXML
    private ImageView player1Image, player2Image, player3Image;

    @FXML
    public void initialize() {
        // نمایش بک‌گراند با استایل در کد جاوا
        String backgroundImage = getClass().getResource("/com/example/img/background.jpeg").toExternalForm();
        System.out.println("Background image URL: " + backgroundImage);  // برای بررسی
        rootVBox.setStyle("-fx-background-image: url('" + backgroundImage + "'); " +
                "-fx-background-size: cover; " +
                "-fx-background-repeat: no-repeat; " +
                "-fx-background-position: center center;");

        // ست کردن تصاویر بازیکن‌ها
        player1Image.setImage(new Image(getClass().getResource("/com/example/img/player1.png").toExternalForm()));
        player2Image.setImage(new Image(getClass().getResource("/com/example/img/player2.png").toExternalForm()));
        player3Image.setImage(new Image(getClass().getResource("/com/example/img/player3.png").toExternalForm()));
    }

    @FXML
    private void selectPlayer1() {
        PlayerData.setSelectedPlayer("player1.png");
        goToStartPage();
    }

    @FXML
    private void selectPlayer2() {
        PlayerData.setSelectedPlayer("player2.png");
        goToStartPage();
    }

    @FXML
    private void selectPlayer3() {
        PlayerData.setSelectedPlayer("player3.png");
        goToStartPage();
    }

    private void goToStartPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project/start_page.fxml"));
            Stage stage = (Stage) player1Image.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
