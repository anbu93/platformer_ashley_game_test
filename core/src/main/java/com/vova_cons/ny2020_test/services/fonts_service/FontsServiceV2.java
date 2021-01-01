package com.vova_cons.ny2020_test.services.fonts_service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anbu on 21.10.19.
 **/
public class FontsServiceV2 implements FontsService {
    private Map<Size, BitmapFont> fontsMap;

    public FontsServiceV2() {
        fontsMap = generateTypeface("source_sans_pro_bold");
    }

    private Map<Size, BitmapFont> generateTypeface(String fontFile) {
        Map<Size, BitmapFont> fonts = new HashMap<Size, BitmapFont>();
        for(Size size : Size.values()) {
            BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/" + fontFile + "_" + size.size + ".fnt"));
            fonts.put(size, font);
        }
        return fonts;
    }

    @Override
    public BitmapFont getFont() {
        return getFont(Size.H2);
    }

    @Override
    public BitmapFont getFont(Size size) {
        return fontsMap.get(size);
    }

    @Override
    public void dispose() {
        for(BitmapFont font : fontsMap.values()) {
            font.dispose();
        }
        fontsMap.clear();
    }
}
