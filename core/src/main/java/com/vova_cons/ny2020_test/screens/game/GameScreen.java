package com.vova_cons.ny2020_test.screens.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.vova_cons.ny2020_test.screens.BaseScreen;
import com.vova_cons.ny2020_test.screens.ScreenType;
import com.vova_cons.ny2020_test.screens.UI;
import com.vova_cons.ny2020_test.screens.game.components.*;
import com.vova_cons.ny2020_test.screens.game.systems.*;
import com.vova_cons.ny2020_test.screens.game.world.GameWorld;
import com.vova_cons.ny2020_test.screens.game.world.GameWorldParser;
import com.vova_cons.ny2020_test.services.ScreensService;
import com.vova_cons.ny2020_test.services.ServiceLocator;
import com.vova_cons.ny2020_test.services.fonts_service.FontsService;
import com.vova_cons.ny2020_test.utils.ViewUtils;

public class GameScreen extends BaseScreen {
    private Engine engine;
    private GameWorld world;

    @Override
    public ScreenType getScreenType() {
        return ScreenType.GameScreen;
    }

    @Override
    public void start() {
        world = new GameWorldParser().parse(1);

        engine = new Engine();
        Entity player = new Entity();
        player.add(new BodyComponent(0, 0, 1f, 1.85f));
        player.add(new PlayerComponent());
        player.add(new VelocityComponent());
        player.add(new GravityComponent());
        player.add(new SpriteComponent(1, SpriteComponent.Type.PlayerIdle));
        engine.addEntity(player);

        Entity camera = new Entity();
        camera.add(new CameraComponent(player));
        engine.addEntity(camera);

        engine.addSystem(new PlayerInputSystem());
        engine.addSystem(new GravitySystem());
        engine.addSystem(new MoveSystem(world));
        engine.addSystem(new PlayerDeathSystem(world));
        engine.addSystem(new GroundStandingSystem(world));
        engine.addSystem(new CameraSystem(UI.SCENE_WIDE_WIDTH / RenderSystem.TILE_SIZE,
                UI.SCENE_HEIGHT/ RenderSystem.TILE_SIZE));
        engine.addSystem(new RenderSystem(world, batch, 1));
    }

    @Override
    public void update(float delta) {
        engine.update(delta);
        if (world.state == GameWorld.PLAYER_DEATH) {
            onGameOver();
            world.state = GameWorld.GAME_OVER;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            ScreensService screensService = ServiceLocator.getService(ScreensService.class);
            screensService.changeScreen(ScreenType.MenuScreen);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            ScreensService screensService = ServiceLocator.getService(ScreensService.class);
            screensService.changeScreen(ScreenType.GameScreen);
        }
    }

    private void onGameOver() {
        VerticalGroup content = new VerticalGroup();
        content.space(50);
        content.align(Align.center);
        content.addActor(ViewUtils.createLabel("GAME OVER!", FontsService.Size.H1, Color.RED));
        content.addActor(ViewUtils.createLabel("Для перезагрузки используйте R", FontsService.Size.H2, Color.RED));
        content.setPosition(UI.SCENE_WIDE_WIDTH/2f, UI.SCENE_HEIGHT/2f, Align.center);
        this.addActor(content);
    }
}
