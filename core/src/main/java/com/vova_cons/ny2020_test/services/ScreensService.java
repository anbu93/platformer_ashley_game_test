package com.vova_cons.ny2020_test.services;

import com.badlogic.gdx.utils.ObjectMap;
import com.vova_cons.ny2020_test.Core;
import com.vova_cons.ny2020_test.screens.BaseScreen;
import com.vova_cons.ny2020_test.screens.ScreenType;

public class ScreensService implements Service {
    private final Core core;
    private ObjectMap<ScreenType, BaseScreen> screensMap = new ObjectMap<>();

    //region interface
    public ScreensService(Core core) {
        this.core = core;
    }

    public void registerScreen(BaseScreen screen) {
        screensMap.put(screen.getScreenType(), screen);
    }

    public void changeScreen(ScreenType screenType) {
        BaseScreen screen = screensMap.get(screenType);
        if (screen != null) {
            core.setScreen(screen);
        }
    }
    //endregion
}
