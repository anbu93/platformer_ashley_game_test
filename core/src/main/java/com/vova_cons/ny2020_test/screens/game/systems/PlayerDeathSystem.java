package com.vova_cons.ny2020_test.screens.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.vova_cons.ny2020_test.screens.game.components.BodyComponent;
import com.vova_cons.ny2020_test.screens.game.utils.Families;
import com.vova_cons.ny2020_test.screens.game.utils.Mappers;
import com.vova_cons.ny2020_test.screens.game.world.GameWorld;
import com.vova_cons.ny2020_test.screens.game.world.TileType;

public class PlayerDeathSystem extends EntitySystem {
    private static final float PRECISION_H = 0.3f;
    private static final float PRECISION_V = 0.3f;
    private final GameWorld world;
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> enemies;

    public PlayerDeathSystem(GameWorld world) {
        this.world = world;
    }

    public PlayerDeathSystem(int priority, GameWorld world) {
        super(priority);
        this.world = world;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Families.player);
        enemies = engine.getEntitiesFor(Families.enemy);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for(Entity player : entities) {
            processPlayer(player);
        }
    }

    private void processPlayer(Entity player) {
        BodyComponent body = Mappers.body.get(player);
        boolean isDeath = checkDeathFromWorldTiles(body);
        if (!isDeath) {
            isDeath = checkDeathFromEnemy(body);
        }
        if (isDeath) {
            world.ui.onPlayerDeath();
            world.state = GameWorld.GAME_OVER;
        }
    }

    private boolean checkDeathFromWorldTiles(BodyComponent body) {
        for(int x =(int) (body.x + PRECISION_H); x < body.x + body.w - PRECISION_H; x++) {
            for(int y =(int) (body.y + PRECISION_V); y < body.y + body.h - PRECISION_V; y++) {
                int tile = world.level.get(x, y);
                if (TileType.isDamageTile(tile)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDeathFromEnemy(BodyComponent body) {
        for(Entity enemy : enemies) {
            BodyComponent enemyBody = Mappers.body.get(enemy);
            if (isCollisions(body, enemyBody)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCollisions(BodyComponent body, BodyComponent enemyBody) {
        return Math.abs(body.x + body.w/2f - enemyBody.x + enemyBody.w/2f)
                        < (body.w/2f + 0.5f*enemyBody.w) &&
                Math.abs(body.y + body.h/2f - enemyBody.y + enemyBody.h/2f)
                        < (body.h/2f + 0.5f*enemyBody.h);
    }
}
