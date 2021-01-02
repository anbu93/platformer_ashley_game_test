package com.vova_cons.ny2020_test.screens.game.world;

public class TileType {
    public static final int EMPTY = 0;
    // 1X - grounds
    public static final int GROUND = 10;
    // 2X - traps
    public static final int TRAP = 20;
    public static final int LAVA = 22;
    // 10X - entities
    public static final int PLAYER = 101;
    // 11X - buttons
    public static final int BUTTON_RED = 111;
    // 12X - coins
    public static final int COIN_GOLD = 121;
    public static final int COIN_SILVER = 122;

    public static boolean isGroundTile(int tile) {
        return tile == GROUND;
    }

    public static boolean isDamageTile(int tile) {
        return tile == TRAP || tile == LAVA;
    }

    public static int detectTile(char symbol) {
        switch(symbol) {
            case '#': return GROUND;
            case ' ': return EMPTY;
            case 'T': return TRAP;
            case 'L': return LAVA;
            case '@': return PLAYER;
            case 'B': return BUTTON_RED;
            case 'g': return COIN_GOLD;
            case 's': return COIN_SILVER;
        }
        return EMPTY;
    }
}
