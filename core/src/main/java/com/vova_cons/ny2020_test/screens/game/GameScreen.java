package com.vova_cons.ny2020_test.screens.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.vova_cons.ny2020_test.screens.BaseScreen;
import com.vova_cons.ny2020_test.screens.ScreenType;
import com.vova_cons.ny2020_test.screens.UI;
import com.vova_cons.ny2020_test.screens.game.components.*;
import com.vova_cons.ny2020_test.screens.game.systems.*;
import com.vova_cons.ny2020_test.screens.game.ui.GameUi;
import com.vova_cons.ny2020_test.screens.game.world.GameWorld;
import com.vova_cons.ny2020_test.screens.game.world.GameWorldParser;
import com.vova_cons.ny2020_test.services.ScreensService;
import com.vova_cons.ny2020_test.services.ServiceLocator;

public class GameScreen extends BaseScreen {
    public static int LEVEL = 1;
    private Engine engine;
    private GameWorld world;
    private GameUi ui;

    @Override
    public ScreenType getScreenType() {
        return ScreenType.GameScreen;
    }

    @Override
    public void start() {
        world = new GameWorldParser().parse(LEVEL);
        if (world != null) {
            ui = new GameUi(LEVEL);
            this.addActor(ui);
            world.ui = ui;

            engine = new Engine();
            Entity player = new Entity();
            player.add(new BodyComponent(world.playerX, world.playerY, 1f, 1.85f));
            player.add(new PlayerComponent());
            player.add(new VelocityComponent());
            player.add(new GravityComponent());
            player.add(new AnimationComponent());
            engine.addEntity(player);

            Entity camera = new Entity();
            camera.add(new CameraComponent(player));
            engine.addEntity(camera);

            for(GameWorld.Enemy enemyData : world.enemies) {
                Entity entity = new Entity();
                switch(enemyData.type) {
                    case Slime:
                        entity.add(new EnemyComponent(EnemyComponent.Type.Slime));
                        entity.add(new SpriteComponent(SpriteComponent.Type.Slime));
                        entity.add(new BodyComponent(enemyData.x, enemyData.y, 0.75f, 0.6f));
                        entity.add(new GravityComponent());
                        break;
                    case Fly:
                        entity.add(new EnemyComponent(EnemyComponent.Type.Fly));
                        entity.add(new SpriteComponent(SpriteComponent.Type.Fly));
                        entity.add(new BodyComponent(enemyData.x, enemyData.y, 0.8f, 0.4f));
                        break;
                }
                entity.add(new VelocityComponent());
                engine.addEntity(entity);
            }

            engine.addSystem(new PlayerInputSystem(world));
            engine.addSystem(new GravitySystem());
            engine.addSystem(new MoveSystem(world));
            engine.addSystem(new PlayerDeathSystem(world));
            engine.addSystem(new GroundStandingSystem(world));
            engine.addSystem(new EnemyMoveSystem());
            engine.addSystem(new MechanismInteractionSystem(world));
            engine.addSystem(new CameraSystem(UI.SCENE_WIDTH / RenderSystem.TILE_SIZE,
                    UI.SCENE_HEIGHT / RenderSystem.TILE_SIZE));
            engine.addSystem(new RenderSystem(world, batch, 1));
        }
    }

    @Override
    public void update(float delta) {
        if (world != null) {
            engine.update(delta);
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                ScreensService screensService = ServiceLocator.getService(ScreensService.class);
                screensService.changeScreen(ScreenType.MenuScreen);
            }
            if (world.state == GameWorld.GAME_OVER && Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                ScreensService screensService = ServiceLocator.getService(ScreensService.class);
                screensService.changeScreen(ScreenType.GameScreen);
            }
            if (world.state == GameWorld.GAME_WIN && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                ScreensService screensService = ServiceLocator.getService(ScreensService.class);
                screensService.changeScreen(ScreenType.GameScreen);
            }
        }
    }
}
