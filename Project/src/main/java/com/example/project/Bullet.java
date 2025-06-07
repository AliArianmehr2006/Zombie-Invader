
package com.example.project;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

public class Bullet extends ImageView {
    private static final double SPEED = 2.5;//سرعت بالا رفتن تیر ها از پایین صفحه به بالا
    private AnimationTimer timer;

    public Bullet(double startX, double startY) {
        super(new Image(Bullet.class.getResource("/com/example/img/bullet.png").toExternalForm()));
        setX(startX);
        setY(startY);
        setFitWidth(40);
        setFitHeight(40);
    }

    public void startMoving() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                setY(getY() - SPEED);

                // حذف گلوله اگر به بالای صفحه رسید
                if (getY() < -40 && getParent() instanceof Pane pane) {
                    pane.getChildren().remove(Bullet.this);
                    stop();
                }
            }
        };
        timer.start();
    }
}

