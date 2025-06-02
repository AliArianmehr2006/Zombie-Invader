package com.example.project;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Zombie extends ImageView {

    public Zombie(double x, double y, String imagePath) {
        try {
            Image image = new Image(getClass().getResource(imagePath).toExternalForm());
            setImage(image);
        } catch (NullPointerException e) {
            System.err.println("⚠ تصویر زامبی یافت نشد: " + imagePath);
            setImage(new Image("https://via.placeholder.com/50x50.png?text=404"));
        }

        setX(x);
        setY(y);
        setFitWidth(65);
        setFitHeight(65);
    }

    public void destroy() {
        this.setVisible(false);
    }

    public abstract double getSpeed();
}
