package com.vova_cons.ny2020_test.screens.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.vova_cons.ny2020_test.screens.game.components.BodyComponent;
import com.vova_cons.ny2020_test.screens.game.components.VelocityComponent;
import com.vova_cons.ny2020_test.screens.game.utils.Families;
import com.vova_cons.ny2020_test.screens.game.utils.Mappers;
import com.vova_cons.ny2020_test.screens.game.world.GameWorld;
import com.vova_cons.ny2020_test.screens.game.world.TileType;

public class MoveSystem extends EntitySystem {
    private static final float PRECISION_H = 0.1f;
    private static final float PRECISION_V = 0.3f;
    private final GameWorld world;
    private ImmutableArray<Entity> entities;

    public MoveSystem(GameWorld world) {
        this.world = world;
    }

    public MoveSystem(GameWorld world, int priority) {
        super(priority);
        this.world = world;
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
        if (world.state == GameWorld.GAME_PROCESS) {
            for (Entity entity : entities) {
                processEntity(entity, deltaTime);
            }
        }
    }

    float nextX, nextY;
    private void processEntity(Entity entity, float deltaTime) {
        BodyComponent body = Mappers.body.get(entity);
        VelocityComponent velocity = Mappers.velocity.get(entity);
        body.x += velocity.x * deltaTime;
        checkGameWorldBorder(body, velocity);
        checkHorizontalCollisions(body, velocity);
        body.y += velocity.y * deltaTime;
        checkGameWorldBorder(body, velocity);
        checkHeadCollision(body, velocity);
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

    private void checkHorizontalCollisions(BodyComponent body, VelocityComponent velocity) {
        for(int x =(int) (body.x + PRECISION_H); x < body.x + body.w - PRECISION_H; x++) {
            for(int y =(int) (body.y + PRECISION_V); y < body.y + body.h - PRECISION_V; y++) {
                int tile = world.level.get(x, y);
                if (TileType.isGroundTile(tile)) {
                    // try shift horizontal
                    if (body.x + body.w/2f < x + 0.1f) {
                        // if left, shift to left
                        body.x = x - body.w + PRECISION_H;
                        velocity.x = 0; // stop moving
                    } else if (body.x + body.w/2f > x + 0.5f) {
                        // if right side, shift to right
                        body.x = x + 1 - PRECISION_H;
                        velocity.x = 0; // stop moving
                    }
                }
            }
        }
    }

    private void checkHeadCollision(BodyComponent body, VelocityComponent velocity) {
        if (!body.grounded && velocity.y > 0) { // is jumping
            int y = (int) (body.y + body.h);
            for (int x = (int) (body.x + PRECISION_H); x < body.x + body.w - PRECISION_H; x++) {
                int tile = world.level.get(x, y);
                if (TileType.isGroundTile(tile)) { // head collision with ground tile, stop jumping
                    velocity.y = 0;
                    body.y = y - body.h;
                    System.out.println("Head hit");
                    return;
                }
            }
        }
    }
}
