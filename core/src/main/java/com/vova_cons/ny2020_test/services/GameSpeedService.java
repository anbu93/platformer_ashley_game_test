package com.vova_cons.ny2020_test.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.vova_cons.ny2020_test.Core;

public class GameSpeedService implements UpdatableService {
    private final Core core;

    public GameSpeedService(Core core) {
        this.core = core;
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            core.setSpeed(1f);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            core.setSpeed(1f / 2f);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            core.setSpeed(1f / 10f);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F4)) {
            core.setSpeed(1f / 20f);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            core.setSpeed(0f);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F6)) {
            core.setSpeed(2f);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F7)) {
            core.setSpeed(4f);
        }
    }
}
