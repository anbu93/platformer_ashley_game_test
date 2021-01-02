package com.vova_cons.ny2020_test.screens.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.vova_cons.ny2020_test.screens.game.components.BodyComponent;
import com.vova_cons.ny2020_test.screens.game.components.VelocityComponent;
import com.vova_cons.ny2020_test.screens.game.utils.Families;
import com.vova_cons.ny2020_test.screens.game.utils.Mappers;
import com.vova_cons.ny2020_test.screens.game.utils.MathUtils;

public class PlayerInputSystem extends EntitySystem {
    private float maxPlayerSpeed = 10; // meter per sec
    private float playerAcceleration = 30; // meter per sec^2
    private float playerDeceleration = 80; // meter per sec^2
    private float jumpSpeed = 20; // meter per sec^2
    private ImmutableArray<Entity> entities;
    private boolean jumpUsed = false;
    private boolean jumpTouchUp = true;
    private int moveDirection = 0;

    public PlayerInputSystem() {}

    public PlayerInputSystem(int priority) {
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
        entities = engine.getEntitiesFor(Families.player);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        calculateInput();
        applyInput(deltaTime);
    }

    private void calculateInput() {
        moveDirection = 0;
        jumpUsed = false;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (jumpTouchUp) {
                jumpUsed = true;
                jumpTouchUp = false;
            }
        } else {
            jumpTouchUp = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveDirection -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveDirection += 1;
        }
    }

    private void applyInput(float deltaTime) {
        for(Entity entity : entities) {
            VelocityComponent velocity = Mappers.velocity.get(entity);
            if (jumpUsed) {
                BodyComponent body = Mappers.body.get(entity);
                if (body.grounded) {
                    velocity.y = jumpSpeed;
                    System.out.println("Jump!");
                }
            }
            if (moveDirection == 0) { // остановка игрока
                if (Math.abs(velocity.x) < 0.10f) { // 10 см/с - слишком малая величина, можно пренебречь
                    velocity.x = 0;
                } else if (velocity.x < 0) { // двигался влево, придаем силу замедления вправо
                    velocity.x += playerDeceleration * deltaTime;
                    if (velocity.x > 0) {
                        velocity.x = 0;
                    }
                } else if (velocity.x > 0) { // двигался вправо, придаем силу замедления влево
                    velocity.x -= playerDeceleration * deltaTime;
                    if (velocity.x < 0) {
                        velocity.x = 0;
                    }
                }
            } else { // игрок двигается
                velocity.x += moveDirection * playerAcceleration * deltaTime;
                velocity.x = MathUtils.limit(velocity.x, -maxPlayerSpeed, maxPlayerSpeed);
            }
        }
    }
}
