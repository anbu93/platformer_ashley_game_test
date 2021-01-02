package com.vova_cons.ny2020_test.screens.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class CameraComponent implements Component {
    public Entity target;
    public float x, y;

    public CameraComponent(Entity target) {
        this.target = target;
    }
}
