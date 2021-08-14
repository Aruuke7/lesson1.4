package com.company;

import java.util.Random;

public class Test {

    static Random random = new Random();


    public static void main(String[] args) {

        if (isStun()) {

        }
        System.out.println(value());
    }

    public static int digit() {
        if (isStun()) {
            return 5;
        } else if (value() > 5) {
            return 10;
        }
        return 100;
    }

    public static int value() {
        int max = 150;
        int min = -50;
        int num = min + random.nextInt(max - min) + 1;
        if (num > 5 && num < -40) {
            value();
            return 0;
        } else
            return num;
    }

    public static boolean isStun() {
        return random.nextBoolean();
    }

}
