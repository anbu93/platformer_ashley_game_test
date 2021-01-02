package com.vova_cons.ny2020_test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.vova_cons.ny2020_test.screens.MainMenuScreen;
import com.vova_cons.ny2020_test.screens.ScreenType;
import com.vova_cons.ny2020_test.screens.game.GameScreen;
import com.vova_cons.ny2020_test.services.ScreensService;
import com.vova_cons.ny2020_test.services.ServiceLocator;
import com.vova_cons.ny2020_test.services.fonts_service.FontsService;
import com.vova_cons.ny2020_test.services.fonts_service.FontsServiceV2;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Core extends Game {
    private Screen nextScreen;
    private float speed = 1f;

    @Override
    public void create() {
        ServiceLocator.register(FontsService.class, new FontsServiceV2());
        ScreensService screensService = new ScreensService(this);
        ServiceLocator.register(ScreensService.class, screensService);

        screensService.registerScreen(new MainMenuScreen());
        screensService.registerScreen(new GameScreen());
        screensService.changeScreen(ScreenType.MenuScreen);
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            speed = 1f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            speed = 1f / 2f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            speed = 1f / 10f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F4)) {
            speed = 1f / 20f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            speed = 0f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F6)) {
            speed = 2f;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F7)) {
            speed = 4f;
        }
        float delta = speed * Gdx.graphics.getDeltaTime();
        if (screen != null) {
            screen.render(delta);
        }
        if (nextScreen != null) {
            super.setScreen(nextScreen);
            nextScreen = null;
        }
    }

    @Override
    public void setScreen(Screen screen) {
        this.nextScreen = screen;
    }
}