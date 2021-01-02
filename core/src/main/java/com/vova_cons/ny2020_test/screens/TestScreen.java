package com.vova_cons.ny2020_test.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class TestScreen extends BaseScreen {
    @Override
    public ScreenType getScreenType() {
        return ScreenType.TestScreen;
    }

    @Override
    public void start() {
        Image bg = new Image(new Texture("metagame_hud.png"));
        bg.setSize(UI.SCENE_WIDTH, UI.SCENE_HEIGHT);
        this.addActor(bg);
    }

    @Override
    public void update(float delta) {}
}
