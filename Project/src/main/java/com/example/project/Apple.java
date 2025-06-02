package com.example.project;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class Apple extends ImageView {
    private final double speed = 2.5;
    private Timeline moveTimeline;

    public Apple(double x, double y) {
        Image image = new Image(getClass().getResource("/com/example/img/apple.png").toExternalForm());
        this.setImage(image);
        this.setFitWidth(40);
        this.setFitHeight(30);
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    public void startMoving() {
        moveTimeline = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            this.setLayoutY(this.getLayoutY() + speed);
        }));
        moveTimeline.setCycleCount(Timeline.INDEFINITE);
        moveTimeline.play();
    }

    public void stopMoving() {
        if (moveTimeline != null) moveTimeline.stop();
    }
}
