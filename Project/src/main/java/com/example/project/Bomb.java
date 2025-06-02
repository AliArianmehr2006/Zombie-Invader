package com.example.project;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Bomb extends ImageView {
    private TranslateTransition transition;

    public Bomb(double x, double y) {
        super(new Image(Bomb.class.getResource("/com/example/img/bomb.png").toExternalForm()));
        setFitWidth(30);
        setFitHeight(30);
        setLayoutX(x);
        setLayoutY(y);
    }

    public void startMoving() {
        transition = new TranslateTransition(Duration.seconds(6), this);
        transition.setToY(1000);
        transition.play();
    }

    public void stopMoving() {
        if (transition != null) transition.stop();
    }
}
