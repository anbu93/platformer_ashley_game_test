package com.vova_cons.ny2020_test.screens.game.components;

import com.badlogic.ashley.core.Component;

public class AnimationComponent implements Component {
    public float time = 0;
    public Type type;
    public int direction = 1;

    public enum Type {
        Idle,
        Walk,
        Jump;
    }
}
