package com.vova_cons.ny2020_test.screens.game.utils;

import com.badlogic.ashley.core.Family;
import com.vova_cons.ny2020_test.screens.game.components.*;

public class Families {
    public static final Family player = Family.one(PlayerComponent.class).get();
    public static final Family withBody = Family.all(BodyComponent.class).get();
    public static final Family moved = Family.all(BodyComponent.class, VelocityComponent.class).get();
    public static final Family gravity = Family.all(GravityComponent.class, VelocityComponent.class).get();
    public static final Family drawing = Family.one(SpriteComponent.class, AnimationComponent.class).get();
    public static final Family camera = Family.one(CameraComponent.class).get();
}
