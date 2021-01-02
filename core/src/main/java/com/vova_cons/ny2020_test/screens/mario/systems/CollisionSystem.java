package com.vova_cons.ny2020_test.screens.mario.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.vova_cons.ny2020_test.screens.mario.components.BodyComponent;
import com.vova_cons.ny2020_test.screens.mario.components.VelocityComponent;
import com.vova_cons.ny2020_test.screens.mario.utils.Families;
import com.vova_cons.ny2020_test.screens.mario.utils.Mappers;
import com.vova_cons.ny2020_test.screens.mario.world.GameWorld;
import com.vova_cons.ny2020_test.screens.mario.world.TileType;

public class CollisionSystem extends EntitySystem {
    private static final float PRECISION_H = 0.1f; // 10 cm precision
    private static final float PRECISION_V = 0.3f; // 10 cm precision
    private final GameWorld world;
    private ImmutableArray<Entity> entities;

    public CollisionSystem(GameWorld world) {
        this.world = world;
    }

    public CollisionSystem(int priority, GameWorld world) {
        super(priority);
        this.world = world;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Families.moved);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for(Entity entity : entities) {
            processEntity(entity, deltaTime);
        }
    }

    private void processEntity(Entity entity, float deltaTime) {
        BodyComponent body = Mappers.body.get(entity);
        VelocityComponent velocity = Mappers.velocity.get(entity);
        checkGameWorldBorder(body, velocity);
        checkHeadCollision(body, velocity);
        checkHorizontalCollisions(body, velocity);
    }

    private void checkGameWorldBorder(BodyComponent body, VelocityComponent velocity) {
        if (body.x < 0) {
            body.x = 0;
            velocity.x = 0;
        }
        if (body.x + body.w > world.level.width) {
            body.x = world.level.width - body.w;
            velocity.x = 0;
        }
        if (body.y < 0) {
            body.y = 0;
            velocity.y = 0;
            body.grounded = true;
        }
        if (body.y + body.h > world.level.height) {
            body.y = world.level.height - body.h;
            velocity.y = 0;
        }
    }

    private void checkHeadCollision(BodyComponent body, VelocityComponent velocity) {
        if (!body.grounded && velocity.y > 0) { // is jumping
            int y = (int) (body.y + body.h);
            for (int x = (int) (body.x + PRECISION_H); x < body.x + body.w - PRECISION_H; x++) {
                int tile = world.level.get(x, y);
                if (tile == TileType.GROUND) { // head collision with ground tile, stop jumping
                    velocity.y = 0;
                    body.y = y - body.h;
                    System.out.println("Head hit");
                    return;
                }
            }
        }
    }

    private void checkHorizontalCollisions(BodyComponent body, VelocityComponent velocity) {
        for(int x =(int) (body.x + PRECISION_H); x < body.x + body.w - PRECISION_H; x++) {
            for(int y =(int) (body.y + PRECISION_V); y < body.y + body.h - PRECISION_V; y++) {
                int tile = world.level.get(x, y);
                if (tile == TileType.GROUND) {
                    // try shift horizontal
                    if (body.x + body.w/2f < x + 0.1f) {
                        // если середина объекта находится слева, сдвиг влево
                        body.x = x - body.w + PRECISION_H;
                        velocity.x = 0; // stop moving
                    } else if (body.x + body.w/2f > x + 0.5f) {
                        // иначе если средина справа, то сдвиг вправо
                        body.x = x + 1 - PRECISION_H;
                        velocity.x = 0; // stop moving
                    }
                }
            }
        }
    }
}
