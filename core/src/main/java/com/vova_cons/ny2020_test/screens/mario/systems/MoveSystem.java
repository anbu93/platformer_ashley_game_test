package com.vova_cons.ny2020_test.screens.mario.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.vova_cons.ny2020_test.screens.mario.components.BodyComponent;
import com.vova_cons.ny2020_test.screens.mario.components.VelocityComponent;
import com.vova_cons.ny2020_test.screens.mario.utils.Families;
import com.vova_cons.ny2020_test.screens.mario.utils.Mappers;

public class MoveSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    public MoveSystem() {
    }

    public MoveSystem(int priority) {
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
        entities = engine.getEntitiesFor(Families.moved);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for(Entity entity : entities) {
            BodyComponent body = Mappers.body.get(entity);
            VelocityComponent velocity = Mappers.velocity.get(entity);
            body.x += velocity.x * deltaTime;
            body.y += velocity.y * deltaTime;
        }
    }
}
