package com.vova_cons.ny2020_test.screens.test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.vova_cons.ny2020_test.screens.BaseScreen;
import com.vova_cons.ny2020_test.screens.ScreenType;
import com.vova_cons.ny2020_test.screens.UI;

public class TestScreen extends BaseScreen {
    @Override
    public ScreenType getScreenType() {
        return ScreenType.TestScreen;
    }

    @Override
    public void start() {
        Image bg = new Image(new Texture("metagame_hud.png"));
        bg.setSize(UI.SCENE_WIDE_WIDTH, UI.SCENE_HEIGHT);
        this.addActor(bg);
    }

    @Override
    public void update(float delta) {}
}
