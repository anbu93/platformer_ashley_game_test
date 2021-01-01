package com.vova_cons.ny2020_test.screens.mario.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.vova_cons.ny2020_test.screens.mario.components.BodyComponent;
import com.vova_cons.ny2020_test.screens.mario.components.GravityComponent;
import com.vova_cons.ny2020_test.screens.mario.components.VelocityComponent;
import com.vova_cons.ny2020_test.screens.mario.utils.Families;
import com.vova_cons.ny2020_test.screens.mario.utils.Mappers;

public class GravitySystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    public GravitySystem() {}

    public GravitySystem(int priority) {
        super(priority);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        updateEntities(engine);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        updateEntities(engine);
    }

    private void updateEntities(Engine engine) {
        entities = engine.getEntitiesFor(Families.gravity);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for(Entity entity : entities) {
            processEntity(entity, deltaTime);
        }
    }

    private void processEntity(Entity entity, float deltaTime) {
        GravityComponent gravity = Mappers.gravity.get(entity);
        VelocityComponent velocity = Mappers.velocity.get(entity);
        BodyComponent body = Mappers.body.get(entity);
        if (!body.grounded) {
            velocity.y += gravity.force * deltaTime;
        }
    }
}
