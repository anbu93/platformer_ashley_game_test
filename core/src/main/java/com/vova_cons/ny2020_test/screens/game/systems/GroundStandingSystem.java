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

public class GroundStandingSystem extends EntitySystem {
    private final GameWorld world;
    private ImmutableArray<Entity> entities;

    public GroundStandingSystem(GameWorld world) {
        this.world = world;
    }

    public GroundStandingSystem(int priority, GameWorld world) {
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
            BodyComponent body = Mappers.body.get(entity);
            VelocityComponent velocity = Mappers.velocity.get(entity);
            processEntity(body, velocity);
        }
    }

    private void processEntity(BodyComponent body, VelocityComponent velocity) {
        if (body.grounded) {
            if (velocity.y > 0) { // jump now
                body.grounded = false;
            } else { // velocity.y == 0 stand on ground in prev frame
                if (!isExistsGroundUnder(body)) {
                    System.out.println("Drop down " + body.x + " " + body.y);
                    body.grounded = false; // start drop down
                }
            }
        } else if (velocity.y < 0) { // on drop down, check grounded now
            if (body.y < 0) { // less than 0 workaround
                body.grounded = true; // set grounded now
                body.y = 0;
                velocity.y = 0; //stop drop down
                return;
            }
            int y = (int) body.y;
            if (body.y - (int) body.y > 0.5f) {
                for (int x = (int) (body.x + 0.3f); x < body.x + body.w - 0.3f; x++) {
                    int tile = world.level.get(x, y);
                    if (tile == TileType.GROUND) {
                        body.grounded = true; // set grounded now
                        body.y = y + 1;
                        velocity.y = 0; //stop drop down
                        return;
                    }
                }
            }
        }
    }

    private boolean isExistsGroundUnder(BodyComponent body) {
        int y = (int) body.y - 1;
        for (int x = (int) (body.x + 0.3f); x < body.x + body.w - 0.3f; x++) {
            int tile = world.level.get(x, y);
            if (tile == TileType.GROUND) {
                return true;
            }
        }
        return false;
    }
}
