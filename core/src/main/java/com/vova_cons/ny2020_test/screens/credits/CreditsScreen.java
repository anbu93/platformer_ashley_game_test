package com.vova_cons.ny2020_test.screens.credits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.vova_cons.ny2020_test.screens.BaseScreen;
import com.vova_cons.ny2020_test.screens.ScreenType;
import com.vova_cons.ny2020_test.screens.UI;
import com.vova_cons.ny2020_test.services.ScreensService;
import com.vova_cons.ny2020_test.services.ServiceLocator;
import com.vova_cons.ny2020_test.services.fonts_service.FontsService;
import com.vova_cons.ny2020_test.utils.ViewUtils;

public class CreditsScreen extends BaseScreen {
    @Override
    public ScreenType getScreenType() {
        return ScreenType.Credits;
    }

    @Override
    public void start() {
        VerticalGroup content = new VerticalGroup();
        content.space(10);
        content.align(Align.center);
        content.addActor(ViewUtils.createLabel("Credits", FontsService.Size.H1, Color.WHITE));
        content.addActor(ViewUtils.createLabel("Arts: http://www.kenney.nl", FontsService.Size.H2, Color.WHITE));
        content.addActor(ViewUtils.createLabel("Code: https://github.com/anbu93", FontsService.Size.H2, Color.WHITE));
        content.setPosition(UI.SCENE_WIDE_WIDTH/2f, UI.SCENE_HEIGHT/2f, Align.center);
        this.addActor(content);

        Label backLabel = ViewUtils.createLabel("Back", FontsService.Size.H2, Color.WHITE);
        backLabel.setPosition(0, 0);
        ViewUtils.clickListener(backLabel, this::onClickBack);
        this.addActor(backLabel);
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            onClickBack();
        }
    }

    private void onClickBack() {
        ScreensService screensService = ServiceLocator.getService(ScreensService.class);
        screensService.changeScreen(ScreenType.MenuScreen);
    }
}
