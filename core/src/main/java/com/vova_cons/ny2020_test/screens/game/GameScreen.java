package com.vova_cons.ny2020_test.screens.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.vova_cons.ny2020_test.screens.BaseScreen;
import com.vova_cons.ny2020_test.screens.ScreenType;
import com.vova_cons.ny2020_test.screens.game.components.*;
import com.vova_cons.ny2020_test.screens.game.systems.*;
import com.vova_cons.ny2020_test.screens.game.world.GameWorld;
import com.vova_cons.ny2020_test.services.ScreensService;
import com.vova_cons.ny2020_test.services.ServiceLocator;

public class GameScreen extends BaseScreen {
    private Engine engine;

    @Override
    public ScreenType getScreenType() {
        return ScreenType.MarioGame;
    }

    @Override
    public void start() {
        GameWorld world = new GameWorld();
        world.init("level_1.txt");

        engine = new Engine();
        Entity player = new Entity();
        player.add(new BodyComponent(0, 0, 1f, 1.85f));
        player.add(new PlayerComponent());
        player.add(new VelocityComponent());
        player.add(new GravityComponent());
        player.add(new SpriteComponent(1, SpriteComponent.Type.PlayerIdle));
        engine.addEntity(player);

        engine.addSystem(new PlayerInputSystem());
        engine.addSystem(new GravitySystem());
        engine.addSystem(new MoveSystem(world));
        engine.addSystem(new GroundStandingSystem(world));
        engine.addSystem(new RenderSystem(world, batch, 1));
    }

    @Override
    public void update(float delta) {
        engine.update(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            ScreensService screensService = ServiceLocator.getService(ScreensService.class);
            screensService.changeScreen(ScreenType.MenuScreen);
        }
    }
}
