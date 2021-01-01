package com.vova_cons.ny2020_test.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.vova_cons.ny2020_test.services.ServiceLocator;
import com.vova_cons.ny2020_test.services.fonts_service.FontsService;
import com.vova_cons.ny2020_test.utils.input.CallbackClickListener;

public class ViewUtils {
    public static Label createLabel(String text, FontsService.Size size, Color color) {
        FontsService fontsService = ServiceLocator.getService(FontsService.class);
        Label.LabelStyle labelStyle = new Label.LabelStyle(fontsService.getFont(size), color);
        return new Label(text, labelStyle);
    }

    public static ClickListener clickListener(Callback callback) {
        return new CallbackClickListener(callback);
    }
    public static void clickListener(Actor actor, Callback callback) {
        actor.addListener(clickListener(callback));
    }

}
