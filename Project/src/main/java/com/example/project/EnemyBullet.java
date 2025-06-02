package com.example.project;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class EnemyBullet extends ImageView {

    private final Timeline moveTimeline;

    public EnemyBullet(double x, double y) {
        super(new Image(EnemyBullet.class.getResource("/com/example/img/enemy_bullet.png").toExternalForm()));
        setFitWidth(40);   // عرض تصویر تیر
        setFitHeight(40);  // ارتفاع تصویر تیر
        setX(x);
        setY(y);

        moveTimeline = new Timeline(new KeyFrame(Duration.millis(30), e -> {
            if (!GameState.isGameOver) {
                setY(getY() + 4);  // حرکت به سمت پایین
            }
        }));
        moveTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void startMoving() {
        moveTimeline.play();
    }

    public void stopMoving() {
        moveTimeline.stop();
    }
}
