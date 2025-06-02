package com.example.project;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class MainPageController implements Initializable {

    @FXML
    private ImageView player_ship;
    @FXML
    private Pane gamePane;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label stageLabel;
    @FXML
    private HBox healthBox;
    @FXML
    private Label healthText;

    private final List<Bomb> bombs = new ArrayList<>();
    private Timeline bombSpawnTimeline;

    private final List<Bullet> bullets = new ArrayList<>();
    private final List<EnemyBullet> enemyBullets = new ArrayList<>();
    private final List<Zombie> zombies = new ArrayList<>();

    private Timeline shootTimeline;
    private Timeline zombieSpawnTimeline;

    private final List<Apple> apples = new ArrayList<>();
    private Timeline appleSpawnTimeline;

    private int score = 0;
    private int currentStage = 1;

    private Player player;
    private int playerHealth;

    private boolean isRecordMode = false;

    public void setRecordMode(boolean recordMode) {
        this.isRecordMode = recordMode;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String selected = PlayerData.getSelectedPlayer();

        switch (selected) {
            case "player1.png":
                player = new PlayerType1();
                playerHealth = player.getHealth();
                break;
            case "player2.png":
                player = new PlayerType2();
                playerHealth = player.getHealth();
                break;
            case "player3.png":
                player = new PlayerType3();
                playerHealth = player.getHealth();
                break;
            default:
                player = new PlayerType2();
                playerHealth = player.getHealth();
        }

        player_ship.setImage(new Image(getClass().getResource("/com/example/img/" + selected).toExternalForm()));

        Platform.runLater(() -> {
            Image bgImage = new Image(getClass().getResource("/com/example/img/background3.jpeg").toExternalForm());
            BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
            BackgroundImage backgroundImage = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            gamePane.setBackground(new Background(backgroundImage));

            spawnApples();
            updateHealthHearts();
            setupKeyListeners();
            startShooting();
            spawnZombies();
            checkCollisions();
            spawnBombs();
        });
    }

    private void setupKeyListeners() {
        gamePane.setOnKeyPressed((KeyEvent event) -> {
            double moveAmount = 15;
            if (event.getCode() == KeyCode.RIGHT) {
                player_ship.setLayoutX(Math.min(player_ship.getLayoutX() + moveAmount, gamePane.getWidth() - player_ship.getFitWidth()));
            } else if (event.getCode() == KeyCode.LEFT) {
                player_ship.setLayoutX(Math.max(player_ship.getLayoutX() - moveAmount, 0));
            }
        });
        gamePane.setFocusTraversable(true);
    }

    private void startShooting() {
        shootTimeline = new Timeline(new KeyFrame(Duration.millis(300), event -> {
            double bulletX = player_ship.getLayoutX() + player_ship.getFitWidth() / 2 - 5;
            double bulletY = player_ship.getLayoutY() - 10;

            if (player instanceof PlayerType3) {
                // تیر دو شاخه
                Bullet bulletLeft = new Bullet(bulletX - 10, bulletY);
                Bullet bulletRight = new Bullet(bulletX + 10, bulletY);
                bullets.add(bulletLeft);
                bullets.add(bulletRight);
                gamePane.getChildren().addAll(bulletLeft, bulletRight);
                bulletLeft.startMoving();
                bulletRight.startMoving();
            } else {
                Bullet bullet = new Bullet(bulletX, bulletY);
                bullets.add(bullet);
                gamePane.getChildren().add(bullet);
                bullet.startMoving();
            }
        }));
        shootTimeline.setCycleCount(Timeline.INDEFINITE);
        shootTimeline.play();
    }

    private void spawnZombies() {
        zombieSpawnTimeline = new Timeline(new KeyFrame(Duration.seconds(2.5), e -> {
            Zombie zombie;

            if (isRecordMode) {
                int rand = new Random().nextInt(3);
                if (rand == 0) {
                    zombie = new ZombieType1(Math.random() * (gamePane.getWidth() - 50), 0);
                } else if (rand == 1) {
                    zombie = new ZombieType2(Math.random() * (gamePane.getWidth() - 50), 0);
                } else {
                    zombie = new ZombieType3(Math.random() * (gamePane.getWidth() - 50), 0, this);
                }
            } else {
                if (currentStage == 1) {
                    zombie = new ZombieType1(Math.random() * (gamePane.getWidth() - 50), 0);
                } else if (currentStage == 2) {
                    zombie = new ZombieType2(Math.random() * (gamePane.getWidth() - 50), 0);
                } else {
                    zombie = new ZombieType3(Math.random() * (gamePane.getWidth() - 50), 0, this);
                }
            }

            gamePane.getChildren().add(zombie);
            zombies.add(zombie);

            Timeline moveTimeline = new Timeline(new KeyFrame(Duration.millis(30), ev -> {
                zombie.setY(zombie.getY() + zombie.getSpeed());
            }));
            moveTimeline.setCycleCount(Animation.INDEFINITE);
            moveTimeline.play();
        }));
        zombieSpawnTimeline.setCycleCount(Timeline.INDEFINITE);
        zombieSpawnTimeline.play();
    }

    private void checkCollisions() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                List<Bullet> bulletsToRemove = new ArrayList<>();
                List<EnemyBullet> enemyBulletsToRemove = new ArrayList<>();
                List<Zombie> zombiesToRemove = new ArrayList<>();
                List<Zombie> zombiesReachedBottom = new ArrayList<>();
                List<EnemyBullet> enemyBulletsHitPlayer = new ArrayList<>();

                // تیر به زامبی برخورد کرد
                for (Bullet bullet : bullets) {
                    for (Zombie zombie : zombies) {
                        if (bullet.getBoundsInParent().intersects(zombie.getBoundsInParent())) {
                            bulletsToRemove.add(bullet);
                            zombiesToRemove.add(zombie);
                            break;
                        }
                    }
                }

                bullets.removeAll(bulletsToRemove);
                for (Bullet b : bulletsToRemove) {
                    gamePane.getChildren().remove(b);
                }

                zombies.removeAll(zombiesToRemove);
                for (Zombie z : zombiesToRemove) {
                    z.destroy();
                    gamePane.getChildren().remove(z);
                }

                for (int i = 0; i < zombiesToRemove.size(); i++) {
                    score++;
                    scoreLabel.setText("امتیاز: " + score);
                    updateStage();
                }

                // تیر دشمن به پلیر خورد
                for (EnemyBullet eb : enemyBullets) {
                    if (eb.getBoundsInParent().intersects(player_ship.getBoundsInParent())) {
                        enemyBulletsHitPlayer.add(eb);
                    } else if (eb.getY() > gamePane.getHeight()) {
                        enemyBulletsToRemove.add(eb);
                    }
                }

                for (EnemyBullet eb : enemyBulletsHitPlayer) {
                    gamePane.getChildren().remove(eb);
                }
                enemyBullets.removeAll(enemyBulletsHitPlayer);

                for (EnemyBullet eb : enemyBulletsToRemove) {
                    gamePane.getChildren().remove(eb);
                }
                enemyBullets.removeAll(enemyBulletsToRemove);

                // کم کردن جان پلیر
                for (EnemyBullet eb : enemyBulletsHitPlayer) {
                    playerHealth--;
                    removeOneHeart();
                    if (playerHealth <= 0) {
                        System.out.println("🧟‍♂️ بازی تمام شد!");
                        gameOver();
                        stop();
                        return;
                    }
                }

                // زامبی به پایین رسید؟
                for (Zombie z : zombies) {
                    if (z.getBoundsInParent().getMaxY() >= gamePane.getHeight()) {
                        zombiesReachedBottom.add(z);
                    }
                }

                if (!zombiesReachedBottom.isEmpty()) {
                    System.out.println("🧟‍♂️ بازی تمام شد! زامبی به پایین رسید.");
                    gameOver();
                    stop();
                }

                // برخورد سیب با پلیر
                List<Apple> applesToRemove = new ArrayList<>();
                for (Apple apple : apples) {
                    if (apple.getBoundsInParent().intersects(player_ship.getBoundsInParent())) {
                        applesToRemove.add(apple);
                        if (playerHealth < 10) {
                            playerHealth++;
                            addOneHeart();
                        }
                    } else if (apple.getLayoutY() > gamePane.getHeight()) {
                        applesToRemove.add(apple);
                    }
                }

                for (Apple apple : applesToRemove) {
                    apple.stopMoving();
                    gamePane.getChildren().remove(apple);
                    apples.remove(apple);
                }
            }
        };
        timer.start();
    }

    private void updateStage() {
        if (!isRecordMode) {
            if (score >= 20) {
                currentStage = 3;
            } else if (score >= 10) {
                currentStage = 2;
            } else {
                currentStage = 1;
            }
            stageLabel.setText("مرحله: " + currentStage);
        }
    }

    private void gameOver() {
        shootTimeline.stop();
        zombieSpawnTimeline.stop();
        if (bombSpawnTimeline != null) bombSpawnTimeline.stop();
        if (appleSpawnTimeline != null) appleSpawnTimeline.stop();
        // نمایش پیغام یا رفتن به صفحه پایان بازی
    }

    private void updateHealthHearts() {
        healthBox.getChildren().clear();
        for (int i = 0; i < playerHealth; i++) {
            ImageView heart = new ImageView(new Image(getClass().getResource("/com/example/img/heart.png").toExternalForm()));
            heart.setFitHeight(20);
            heart.setFitWidth(20);
            healthBox.getChildren().add(heart);
        }
        healthText.setText("جان =");
    }

    private void removeOneHeart() {
        if (healthBox.getChildren().size() > 0) {
            healthBox.getChildren().remove(healthBox.getChildren().size() - 1);
        }
    }

    private void addOneHeart() {
        ImageView heart = new ImageView(new Image(getClass().getResource("/com/example/img/heart.png").toExternalForm()));
        heart.setFitHeight(20);
        heart.setFitWidth(20);
        healthBox.getChildren().add(heart);
    }

    private void spawnApples() {
        appleSpawnTimeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
            Apple apple = new Apple(Math.random() * (gamePane.getWidth() - 30), 0);
            apples.add(apple);
            gamePane.getChildren().add(apple);
            apple.startMoving();
        }));
        appleSpawnTimeline.setCycleCount(Timeline.INDEFINITE);
        appleSpawnTimeline.play();
    }

    private void spawnBombs() {
        bombSpawnTimeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
            Bomb bomb = new Bomb(Math.random() * (gamePane.getWidth() - 30), 0);
            bombs.add(bomb);
            gamePane.getChildren().add(bomb);
            bomb.startMoving();
        }));
        bombSpawnTimeline.setCycleCount(Timeline.INDEFINITE);
        bombSpawnTimeline.play();
    }

    // متدهای لازم برای اضافه کردن تیرهای دشمن
    public void addEnemyBullet(EnemyBullet bullet) {
        enemyBullets.add(bullet);
        Platform.runLater(() -> gamePane.getChildren().add(bullet));
        bullet.startMoving();
    }

}
