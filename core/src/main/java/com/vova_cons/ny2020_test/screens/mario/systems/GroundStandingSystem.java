package com.vova_cons.ny2020_test.screens.mario.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.vova_cons.ny2020_test.screens.mario.components.BodyComponent;
import com.vova_cons.ny2020_test.screens.mario.components.VelocityComponent;
import com.vova_cons.ny2020_test.screens.mario.utils.Families;
import com.vova_cons.ny2020_test.screens.mario.utils.Mappers;

public class GroundStandingSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

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
            if (body.grounded) {
                if (velocity.y > 0) {
                    body.grounded = false;
                }
            } else { // on air, check grounded now
                if (body.y <= 0) {
                    body.grounded = true;
                    body.y = 0;
                    velocity.y = 0; //stop drop down
                }
            }
        }
    }
}
