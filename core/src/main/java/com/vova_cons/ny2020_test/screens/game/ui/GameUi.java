package com.vova_cons.ny2020_test.screens.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.vova_cons.ny2020_test.screens.UI;
import com.vova_cons.ny2020_test.screens.game.GameScreen;
import com.vova_cons.ny2020_test.screens.game.world.TileType;
import com.vova_cons.ny2020_test.services.fonts_service.FontsService;
import com.vova_cons.ny2020_test.utils.ViewUtils;

public class GameUi extends Group {
    private int money = 0;
    private final Label scoreLabel;

    public GameUi(int level) {
        Label levelLabel = ViewUtils.createLabel("Level: " + level, FontsService.Size.H1, Color.RED);
        levelLabel.setPosition(UI.SCENE_WIDTH /2f, UI.SCENE_HEIGHT-10, Align.top);
        this.addActor(levelLabel);

        scoreLabel = ViewUtils.createLabel("Score: 0", FontsService.Size.H1, Color.RED);
        scoreLabel.setPosition(10, UI.SCENE_HEIGHT-10, Align.topLeft);
        this.addActor(scoreLabel);
        onTakeCoin(0);
    }

    public void onPlayerDeath() {
        VerticalGroup content = new VerticalGroup();
        content.space(50);
        content.align(Align.center);
        content.addActor(ViewUtils.createLabel("GAME OVER!", FontsService.Size.H1, Color.RED));
        content.addActor(ViewUtils.createLabel("For restart press R", FontsService.Size.H2, Color.RED));
        content.setPosition(UI.SCENE_WIDTH /2f, UI.SCENE_HEIGHT/2f, Align.center);
        this.addActor(content);
    }

    public void onPlayerWin() {
        VerticalGroup content = new VerticalGroup();
        content.space(50);
        content.align(Align.center);
        content.addActor(ViewUtils.createLabel("YOU WIN!", FontsService.Size.H1, Color.RED));
        content.addActor(ViewUtils.createLabel("For continue press ENTER", FontsService.Size.H2, Color.RED));
        content.setPosition(UI.SCENE_WIDTH /2f, UI.SCENE_HEIGHT/2f, Align.center);
        this.addActor(content);
        GameScreen.LEVEL++;
    }

    public void onTakeCoin(int tile) {
        if (tile == TileType.COIN_GOLD) {
            money += 100;
        }
        if (tile == TileType.COIN_SILVER) {
            money += 10;
        }
        if (tile == TileType.COIN_BRONZE) {
            money += 1;
        }
        updateScoreLabel();
    }

    public void addEnemyKillScore(int score) {
        money += score;
        updateScoreLabel();
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Coins: " + money);
    }
}
