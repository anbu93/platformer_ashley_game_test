package com.vova_cons.ny2020_test.screens.mario.world;

public class TileMap {
    public final int width, height;
    private final int[][] array;
    public int defValue = TileType.GROUND;

    public TileMap(int width, int height) {
        this.width = width;
        this.height = height;
        array = new int[width][height];
    }

    public int get(int x, int y) {
        if (isValid(x, y)) {
            return array[x][y];
        }
        return defValue;
    }

    public void set(int x, int y, int value) {
        if (isValid(x, y)) {
            array[x][y] = value;
        }
    }

    public void swap(int x1, int y1, int x2, int y2) {
        if (isValid(x1, y1) && isValid(x2, y2)) {
            int tmp = array[x1][y1];
            array[x1][y1] = array[x2][y2];
            array[x2][y2] = tmp;
        }
    }

    public void foreach(TileMapIterator iterator) {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                iterator.accept(x, y, array[x][y]);
            }
        }
    }

    private boolean isValid(float x, float y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
