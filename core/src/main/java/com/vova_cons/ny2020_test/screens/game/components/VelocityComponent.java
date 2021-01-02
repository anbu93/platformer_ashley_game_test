package com.vova_cons.ny2020_test.screens.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent implements Component {
    public float x, y;

    public void set(float dx, float dy) {
        this.x = dx;
        this.y = dy;
    }

    public Vector2 get(Vector2 velocity) {
        return velocity.set(x, y);
    }
}
