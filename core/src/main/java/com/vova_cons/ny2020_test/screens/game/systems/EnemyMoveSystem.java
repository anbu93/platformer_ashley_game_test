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
import com.vova_cons.ny2020_test.screens.game.world.GameWorld;
import com.vova_cons.ny2020_test.screens.game.world.TileType;
import com.vova_cons.ny2020_test.utils.RandomUtils;

public class EnemyMoveSystem extends EntitySystem {
    private final GameWorld world;
    private ImmutableArray<Entity> entities;

    public EnemyMoveSystem(GameWorld world) {
        this.world = world;
    }

    public EnemyMoveSystem(int priority, GameWorld world) {
        super(priority);
        this.world = world;
    }

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
        boolean isUpdated = false;
        switch(enemy.type) {
            case Slime:
                if (velocity.x == 0) { // start move on game start
                    enemy.direction = -enemy.direction;
                    isUpdated = true;
                }
                if (!isNextTileExists(body, enemy.direction)) {
                    enemy.direction = -enemy.direction;
                    isUpdated = true;
                }
                break;
            case Fly:
                if (velocity.x == 0) { // if enemy stack of border
                    enemy.direction = -enemy.direction;
                    isUpdated = true;
                }
                break;
        }
        if (isUpdated) {
            velocity.x = enemy.direction * getSpeed(enemy.type);
            sprite.flipX = enemy.direction > 0;
        }
    }

    private boolean isNextTileExists(BodyComponent body, int direction) {
        int y = (int) body.y - 1;
        if (Math.abs((body.x+body.w/2f) - (int)(body.x + body.w/2f) - 0.5f) < 0.1f) {
            int x = (int) (body.x + body.w / 2f);
            int tile = world.level.get(x + direction, y);
            return TileType.isGroundTile(tile);
        }
        return true;
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
