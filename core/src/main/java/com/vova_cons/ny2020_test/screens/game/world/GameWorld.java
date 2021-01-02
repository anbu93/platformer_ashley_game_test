package com.vova_cons.ny2020_test.screens.game.world;

import com.vova_cons.ny2020_test.utils.RandomUtils;

public class GameWorld {
    public TileMap level;

    public void init(String file) {
        level = new TileMap(30, 15);
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
