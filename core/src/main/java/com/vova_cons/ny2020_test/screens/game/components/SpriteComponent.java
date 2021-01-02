package com.vova_cons.ny2020_test.screens.game.components;

import com.badlogic.ashley.core.Component;

public class SpriteComponent implements Component {
    public int z = 0;
    public Type type;

    public SpriteComponent(Type type) {
        this.type = type;
    }

    public SpriteComponent(int z, Type type) {
        this.z = z;
        this.type = type;
    }

    public enum Type {
        PlayerIdle,
        Slime,
        Fly
    }
}
