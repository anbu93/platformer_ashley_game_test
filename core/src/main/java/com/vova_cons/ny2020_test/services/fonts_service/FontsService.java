package com.vova_cons.ny2020_test.services.fonts_service;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.vova_cons.ny2020_test.services.Service;

/**
 * Created by anbu on 23.01.19.
 * Старый класс созданный алексеем.
 */
public interface FontsService extends Service {
    BitmapFont getFont();
    BitmapFont getFont(Size sizeName);
    void dispose();

    enum Size {
        H1(41), H2(30);

        int size;
        Size(int size) {
            this.size = size;
        }
    }
}
