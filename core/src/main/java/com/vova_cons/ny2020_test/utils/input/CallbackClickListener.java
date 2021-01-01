package com.vova_cons.ny2020_test.utils.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.vova_cons.ny2020_test.utils.Callback;

public class CallbackClickListener extends ClickListener {
    private final Callback callback;

    public CallbackClickListener(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        if (event.getPointer() == 0) {
            callback.handle();
        }
    }
}
