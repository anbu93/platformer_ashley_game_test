package com.vova_cons.ny2020_test.screens.mario.world;

import com.vova_cons.ny2020_test.utils.RandomUtils;

public class GameWorld {
    public TileMap level;

    public void init(String file) {
        level = new TileMap(10, 10);
        level.defValue = TileType.EMPTY;
        for(int i = 0; i < level.width*level.height*0.3f; i++) {
            level.set(RandomUtils.random.nextInt(level.width), RandomUtils.random.nextInt(level.height), TileType.GROUND);
        }
    }
}
