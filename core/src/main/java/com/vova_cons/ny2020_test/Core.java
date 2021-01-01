package com.vova_cons.ny2020_test;

import com.badlogic.gdx.Game;
import com.vova_cons.ny2020_test.screens.MainMenuScreen;
import com.vova_cons.ny2020_test.screens.ScreenType;
import com.vova_cons.ny2020_test.screens.mario.GameScreen;
import com.vova_cons.ny2020_test.services.ScreensService;
import com.vova_cons.ny2020_test.services.ServiceLocator;
import com.vova_cons.ny2020_test.services.fonts_service.FontsService;
import com.vova_cons.ny2020_test.services.fonts_service.FontsServiceV2;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Core extends Game {
    @Override
    public void create() {
        ServiceLocator.register(FontsService.class, new FontsServiceV2());
        ScreensService screensService = new ScreensService(this);
        ServiceLocator.register(ScreensService.class, screensService);

        screensService.registerScreen(new MainMenuScreen());
        screensService.registerScreen(new GameScreen());
        screensService.changeScreen(ScreenType.MenuScreen);
    }
}