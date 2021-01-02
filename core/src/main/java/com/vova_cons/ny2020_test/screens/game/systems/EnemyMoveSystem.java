package com.vova_cons.ny2020_test.screens.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.vova_cons.ny2020_test.screens.game.components.BodyComponent;
import com.vova_cons.ny2020_test.screens.game.components.EnemyComponent;
import com.vova_cons.ny2020_test.screens.game.components.SpriteComponent;
import com.vova_cons.ny2020_test.screens.game.components.VelocityComponent;
import com.vova_cons.ny2020_test.screens.game.utils.Families;
import com.vova_cons.ny2020_test.screens.game.utils.Mappers;
import com.vova_cons.ny2020_test.utils.RandomUtils;

public class EnemyMoveSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Families.enemy);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for(Entity entity : entities) {
            processEntity(entity, deltaTime);
        }
    }

    private void processEntity(Entity entity, float deltaTime) {
        VelocityComponent velocity = Mappers.velocity.get(entity);
        BodyComponent body = Mappers.body.get(entity);
        EnemyComponent enemy = Mappers.enemy.get(entity);
        SpriteComponent sprite = Mappers.sprite.get(entity);
        if (velocity.x == 0) {
            velocity.x = 0;
            enemy.direction = -enemy.direction;
        } else if (!body.grounded) {
            body.grounded = true;
            velocity.x = 0;
            enemy.direction = -enemy.direction;
        }
        velocity.x = enemy.direction * getSpeed(enemy.type);
        sprite.flipX = enemy.direction > 0;
    }

    private float getSpeed(EnemyComponent.Type type) {
        switch (type) {
            case Slime:
                return 3f; // m/sec
            case Fly:
                return 10f; // m/sec
        }
        return 1f;
    }
}
