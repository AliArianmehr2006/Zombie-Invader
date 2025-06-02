package com.example.project;

import javafx.scene.image.ImageView;

public abstract class Player extends ImageView {
    protected int health;

    public Player() {
        super();
    }

    public int getHealth() {
        return health;
    }
}
