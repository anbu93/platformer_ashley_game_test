package com.vova_cons.ny2020_test.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by anbu on 12.06.19.
 **/
public abstract class BaseScreen implements Screen {
    public static float WIDTH = UI.SCENE_WIDTH;
    public static float HEIGHT = UI.SCENE_HEIGHT;
    public Stage stage;
    public Batch batch;
    public FitViewport viewport;
    private Group content = new Group();

    public abstract ScreenType getScreenType();

    public void create() {
        float ratio = Gdx.graphics.getHeight() / HEIGHT;
        WIDTH = Gdx.graphics.getWidth() / ratio;
        viewport = new FitViewport(WIDTH, HEIGHT);
    }
    @Override
    public void show() {
        create();
        createBatchStageMultiplexer();
        start();
    }

    protected void createBatchStageMultiplexer() {
        batch = new PolygonSpriteBatch(8000);
        stage = new Stage(viewport, batch);
        stage.addActor(content);
        InputMultiplexer multiplexer = new InputMultiplexer(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    public abstract void start();

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        stage.act(delta);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        update(delta);
        stage.draw();
    }

    public abstract void update(float delta);

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        if(stage != null) {
            stage.dispose();
            stage = null;
        }
        if(batch != null) {
            batch.dispose();
            batch = null;
        }
        content.clear();
    }

    public void addActor(Actor actor) {
        content.addActor(actor);
    }
}
