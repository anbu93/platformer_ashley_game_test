package com.vova_cons.ny2020_test.utils;


import com.badlogic.gdx.math.Rectangle;

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

    public static Rectangle fitIn(Rectangle rect, float width, float height) {
        float scale = Math.min(width/rect.width, height/rect.height);
        rect.x = width/2f - rect.width*scale/2f;
        rect.y = height/2f - rect.height*scale/2f;
        rect.width = rect.width*scale;
        rect.height = rect.height*scale;
        return rect;
    }

    public static Rectangle fitOut(Rectangle rect, float width, float height) {
        float scale = Math.max(width/rect.width, height/rect.height);
        rect.x = width/2f - rect.width*scale/2f;
        rect.y = height/2f - rect.height*scale/2f;
        rect.width = rect.width*scale;
        rect.height = rect.height*scale;
        return rect;
    }
}
