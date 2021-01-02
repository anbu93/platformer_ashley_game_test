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

public class MechanismInteractionSystem extends EntitySystem {
    private static final float PRECISION_V = 0.3f;
    private static final float PRECISION_H = 0.3f;
    private final GameWorld world;
    private ImmutableArray<Entity> entities;

    public MechanismInteractionSystem(GameWorld world) {
        this.world = world;
    }

    public MechanismInteractionSystem(int priority, GameWorld world) {
        super(priority);
        this.world = world;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Families.player);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for(Entity entity : entities) {
            processEntity(entity);
        }
    }

    private void processEntity(Entity entity) {
        BodyComponent body = Mappers.body.get(entity);
        for(int x =(int) (body.x + PRECISION_H); x < body.x + body.w - PRECISION_H; x++) {
            for(int y =(int) (body.y + PRECISION_V); y < body.y + body.h - PRECISION_V; y++) {
                int tile = world.level.get(x, y);
                if (TileType.isTargetTile(tile)) {
                    world.level.set(x, y, TileType.getActiveButtonTile(tile));
                    world.ui.onPlayerWin();
                    world.state = GameWorld.GAME_WIN;
                }
                if (TileType.isCoinsTile(tile)) {
                    world.level.set(x, y, TileType.EMPTY);
                    world.ui.onTakeCoin(tile);
                }
            }
        }
    }
}
