package com.vova_cons.ny2020_test.screens.game.components;

import com.badlogic.ashley.core.Component;
import com.vova_cons.ny2020_test.utils.RandomUtils;

public class EnemyComponent implements Component {
    public Type type;
    public int direction = RandomUtils.random.nextBoolean() ? 1 : -1;

    public EnemyComponent(Type type) {
        this.type = type;
    }

    public enum Type {
        Slime,
        Fly
    }
}
