package com.example.project;

public class ZombieType1 extends Zombie {

    public ZombieType1(double x, double y) {
        super(x, y, "/com/example/img/zombie1.png");
    }

    @Override
    public double getSpeed() {
        return 1.5;
    }
}
