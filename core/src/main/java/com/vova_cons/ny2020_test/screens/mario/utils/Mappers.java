package com.vova_cons.ny2020_test.screens.mario.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.vova_cons.ny2020_test.screens.mario.components.*;

public class Mappers {
    public static final ComponentMapper<BodyComponent> body = ComponentMapper.getFor(BodyComponent.class);
    public static final ComponentMapper<VelocityComponent> velocity = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<SpriteComponent> sprite = ComponentMapper.getFor(SpriteComponent.class);
    public static final ComponentMapper<GravityComponent> gravity = ComponentMapper.getFor(GravityComponent.class);
}
