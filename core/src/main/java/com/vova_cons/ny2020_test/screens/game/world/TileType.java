package com.vova_cons.ny2020_test.screens.game.world;

public class TileType {
    public static final int EMPTY = 0;
    // 1X - grounds
    public static final int GROUND = 10;
    public static final int GROUND_GRASS = 11;
    // 2X - traps
    public static final int TRAP = 20;
    public static final int LAVA = 22;
    // 10X - entities
    public static final int PLAYER = 101;
    public static final int SLIME = 102;
    public static final int FLY = 103;
    // 11X - buttons
    public static final int BUTTON_RED = 111;
    public static final int BUTTON_RED_ACTIVE = 112;
    // 12X - coins
    public static final int COIN_GOLD = 121;
    public static final int COIN_SILVER = 122;
    public static final int COIN_BRONZE = 123;

    public static boolean isGroundTile(int tile) {
        return tile == GROUND || tile == GROUND_GRASS;
    }

    public static boolean isDamageTile(int tile) {
        return tile == TRAP || tile == LAVA;
    }

    public static boolean isTargetTile(int tile) {
        return tile == BUTTON_RED;
    }

    public static boolean isCoinsTile(int tile) {
        return tile == COIN_GOLD || tile == COIN_SILVER;
    }

    public static int detectTile(char symbol) {
        switch(symbol) {
            case '#': return GROUND_GRASS;
            case '*': return GROUND;
            case ' ': return EMPTY;
            case 'T': return TRAP;
            case 'L': return LAVA;
            case '@': return PLAYER;
            case 'S': return SLIME;
            case 'F': return FLY;
            case 'B': return BUTTON_RED;
            case 'g': return COIN_GOLD;
            case 's': return COIN_SILVER;
        }
        return EMPTY;
    }

    public static int getActiveButtonTile(int buttonTile) {
        switch(buttonTile) {
            case BUTTON_RED:
                return BUTTON_RED_ACTIVE;
        }
        return EMPTY;
    }
}
