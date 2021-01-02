package com.vova_cons.ny2020_test.screens.game.utils;

public class MathUtils {
    public static float limit(float value, float minValue, float maxValue) {
        if (value < minValue) {
            value = minValue;
        }
        if (value > maxValue) {
            value = maxValue;
        }
        return value;
    }
}
