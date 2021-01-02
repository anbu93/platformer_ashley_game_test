package com.vova_cons.ny2020_test.screens.game.world;

@FunctionalInterface
public interface TileMapIterator {
    void accept(int x, int y, int value);
}
