package com.example.project;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ZombieType3 extends Zombie {

    private final MainPageController controller;
    private final Timeline shootTimeline;

    public ZombieType3(double x, double y, MainPageController controller) {
        super(x, y, "/com/example/img/zombie3.png");
        this.controller = controller;
        this.shootTimeline = createShootTimeline();
        shootTimeline.play();
    }

    private Timeline createShootTimeline() {
        return new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            if (!GameState.isGameOver) {
                EnemyBullet bullet = new EnemyBullet(
                        getX() + getFitWidth() / 2,
                        getY() + getFitHeight()
                );
                controller.addEnemyBullet(bullet);
            }
        }));
    }

    @Override
    public double getSpeed() {
        return 1.5;
    }

    @Override
    public void destroy() {
        super.destroy();
        shootTimeline.stop(); // متوقف کردن تیراندازی هنگام نابودی
    }
}
