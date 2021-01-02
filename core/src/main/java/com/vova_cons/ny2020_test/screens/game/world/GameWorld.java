package com.vova_cons.ny2020_test.screens.game.world;

import com.vova_cons.ny2020_test.screens.game.ui.GameUi;
import com.vova_cons.ny2020_test.utils.RandomUtils;

public class GameWorld {
    public static final int GAME_PROCESS = 0;
    public static final int GAME_OVER = 1;
    public static final int GAME_WIN = 2;
    public int state = GAME_PROCESS;
    public TileMap level;
    public GameUi ui;

    public void random(int width, int height) {
        level = new TileMap(width, height);
        level.defValue = TileType.GROUND;
        for(int i = 0; i < level.width*level.height*0.2f; i++) {
            level.set(RandomUtils.random.nextInt(level.width),
                    RandomUtils.random.nextInt(level.height), TileType.GROUND);
        }

        for(int y = level.height-1; y >= 0; y--) {
            for(int x = 0; x < level.width; x++) {
                switch (level.get(x, y)) {
                    case TileType.GROUND: System.out.print("#"); break;
                    case TileType.EMPTY: System.out.print(" "); break;
                }
            }
            System.out.println();
        }
    }
}
