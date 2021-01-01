package com.vova_cons.ny2020_test.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.vova_cons.ny2020_test.services.ScreensService;
import com.vova_cons.ny2020_test.services.ServiceLocator;
import com.vova_cons.ny2020_test.services.fonts_service.FontsService;
import com.vova_cons.ny2020_test.utils.ViewUtils;

public class MainMenuScreen extends BaseScreen {
    private final ScreensService screensService = ServiceLocator.getService(ScreensService.class);

    @Override
    public ScreenType getScreenType() {
        return ScreenType.MenuScreen;
    }

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void start() {
        VerticalGroup container = new VerticalGroup();
        container.space(50);
        Label marioCloneLabel = ViewUtils.createLabel("Start", FontsService.Size.H1, Color.WHITE);
        container.addActor(marioCloneLabel);
        ViewUtils.clickListener(marioCloneLabel, this::clickedMario);
        marioCloneLabel.setDebug(true);

        Label exitLabel = ViewUtils.createLabel("Exit game", FontsService.Size.H1, Color.WHITE);
        container.addActor(exitLabel);
        ViewUtils.clickListener(exitLabel, this::clickedExit);
        exitLabel.setDebug(true);

        container.setPosition(UI.SCENE_WIDE_WIDTH/2f, UI.SCENE_HEIGHT/2f, Align.center);
        this.addActor(container);
    }

    @Override
    public void update(float delta) {}

    //region logic
    private void clickedMario() {
        screensService.changeScreen(ScreenType.MarioGame);
    }

    private void clickedExit() {
        Gdx.app.exit();
    }
    //endregion
}
