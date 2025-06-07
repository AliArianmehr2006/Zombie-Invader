package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class Game extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project/playerSelection.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Zombie-Invader");
        stage.setScene(scene);
        stage.show();

//        SoundThread t = new SoundThread();
//        t.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
