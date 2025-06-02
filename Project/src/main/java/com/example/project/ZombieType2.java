package com.example.project;

public class ZombieType2 extends Zombie {

    public ZombieType2(double x, double y) {
        super(x, y, "/com/example/img/zombie2.png"); // ✅ تصویر مخصوص تیپ ۲
    }

    @Override
    public double getSpeed() {
        return 3.0; // سرعت دو برابر نسبت به بقیه
    }
}
