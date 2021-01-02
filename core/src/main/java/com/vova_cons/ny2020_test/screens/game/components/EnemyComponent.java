package com.vova_cons.ny2020_test.screens.game.components;

import com.badlogic.ashley.core.Component;

public class EnemyComponent implements Component {
    public Type type;

    public EnemyComponent(Type type) {
        this.type = type;
    }

    public enum Type {
        Slime,
        Fly
    }
}
