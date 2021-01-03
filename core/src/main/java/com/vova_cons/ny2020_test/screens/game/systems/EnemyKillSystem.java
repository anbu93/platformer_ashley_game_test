package com.vova_cons.ny2020_test.screens.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.vova_cons.ny2020_test.screens.game.components.*;
import com.vova_cons.ny2020_test.screens.game.utils.Families;
import com.vova_cons.ny2020_test.screens.game.utils.Mappers;
import com.vova_cons.ny2020_test.screens.game.world.GameWorld;

public class EnemyKillSystem extends EntitySystem {
    private final GameWorld world;
    private ImmutableArray<Entity> players;
    private ImmutableArray<Entity> enemies;

    public EnemyKillSystem(GameWorld world) {
        this.world = world;
    }

    public EnemyKillSystem(int priority, GameWorld world) {
        super(priority);
        this.world = world;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        players = engine.getEntitiesFor(Families.player);
        enemies = engine.getEntitiesFor(Families.enemy);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for(Entity player : players) {
            processPlayer(player);
        }
    }

    Rectangle playerRect = new Rectangle();
    Rectangle enemyRect = new Rectangle();
    private void processPlayer(Entity player) {
        BodyComponent body = Mappers.body.get(player);
        playerRect = body.get(playerRect);
        for(Entity enemy : enemies) {
            BodyComponent enemyBody = Mappers.body.get(enemy);
            enemyRect = enemyBody.get(enemyRect);
            if (playerRect.overlaps(enemyRect)) {
                boolean isPlayerFootInEnemyBody = enemyRect.contains(playerRect.x, playerRect.y)
                        || enemyRect.contains(playerRect.x + playerRect.width/2f, playerRect.y)
                        || enemyRect.contains(playerRect.x + playerRect.width, playerRect.y);
                boolean isPlayerFootTopOfEnemyBodyCenter = playerRect.y > enemyRect.y + enemyRect.height/2f;
                if (isPlayerFootInEnemyBody && isPlayerFootTopOfEnemyBodyCenter) {
                    killEnemy(enemy);
                }
            }
        }
    }

    private void killEnemy(Entity entity) {
        EnemyComponent enemy = Mappers.enemy.get(entity);
        addScore(enemy.type);
        changeEnemySpriteToKilled(enemy.type, entity);
        entity.add(new GravityComponent());
        entity.remove(EnemyComponent.class);
        VelocityComponent velocity = Mappers.velocity.get(entity);
        velocity.x = 0;
    }

    private void changeEnemySpriteToKilled(EnemyComponent.Type type, Entity entity) {
        SpriteComponent sprite = Mappers.sprite.get(entity);
        switch(type) {
            case Slime:
                sprite.type = SpriteComponent.Type.SlimeDeath;
                break;
            case Fly:
                sprite.type = SpriteComponent.Type.FlyDeath;
                break;
        }
    }

    private void addScore(EnemyComponent.Type type) {
        switch(type) {
            case Slime:
                world.ui.addEnemyKillScore(10);
                break;
            case Fly:
                world.ui.addEnemyKillScore(50);
                break;
        }
    }
}
