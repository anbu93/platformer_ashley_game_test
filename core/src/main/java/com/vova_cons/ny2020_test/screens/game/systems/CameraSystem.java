package com.vova_cons.ny2020_test.screens.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.vova_cons.ny2020_test.screens.UI;
import com.vova_cons.ny2020_test.screens.game.components.BodyComponent;
import com.vova_cons.ny2020_test.screens.game.components.CameraComponent;
import com.vova_cons.ny2020_test.screens.game.utils.Families;
import com.vova_cons.ny2020_test.screens.game.utils.Mappers;

public class CameraSystem extends EntitySystem {
    private final float width, height;
    private ImmutableArray<Entity> entities;

    public CameraSystem(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public CameraSystem(float width, float height, int priority) {
        super(priority);
        this.width = width;
        this.height = height;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        entities = engine.getEntitiesFor(Families.camera);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for(Entity entity : entities) {
            processEntity(entity);
        }
    }

    private void processEntity(Entity entity) {
        CameraComponent camera = Mappers.camera.get(entity);
        BodyComponent body = Mappers.body.get(camera.target);
        camera.x = Math.max(0, body.x+body.w/2f - width/2f);
        camera.y = Math.max(0, body.y+body.h/2f - height/2f);
    }
}
