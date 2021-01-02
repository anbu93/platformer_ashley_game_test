package com.vova_cons.ny2020_test.screens.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class BodyComponent implements Component {
    public float x, y, w, h;
    public boolean grounded = true;

    public BodyComponent() {}

    public BodyComponent(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void set(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(float w, float h) {
        this.w = w;
        this.h = h;
    }

    public Vector2 getPosition(Vector2 position) {
        return position.set(x, y);
    }

    public Vector2 getSize(Vector2 size) {
        return size.set(w, h);
    }
}
