package com.vova_cons.ny2020_test.screens.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.Scanner;

public class GameWorldParser {
    public GameWorld parse(int level) {
        String levelPath = "levels/lvl_" + level + ".txt";
        FileHandle levelFile = Gdx.files.internal(levelPath);
        try {
            Scanner scanner = new Scanner(levelFile.read());
            int width = Integer.parseInt(scanner.nextLine());
            int height = Integer.parseInt(scanner.nextLine());
            GameWorld world = new GameWorld();
            world.level = new TileMap(width, height);
            world.level.defValue = TileType.GROUND;
            for(int line = 0; line < height; line++) {
                String tileLine = scanner.nextLine();
                char[] tiles = tileLine.toCharArray();
                for(int x = 0; x < Math.min(tileLine.length(), width); x++) {
                    int tile = TileType.detectTile(tiles[x]);
                    world.level.set(x, height - line - 1, tile);
                }
            }
            return world;
        } catch (Exception e) {
            throw new RuntimeException("Error with parsing level " + levelPath, e);
        }
    }
}
