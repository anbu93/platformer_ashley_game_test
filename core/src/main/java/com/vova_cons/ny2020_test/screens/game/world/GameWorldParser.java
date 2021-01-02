package com.vova_cons.ny2020_test.screens.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.vova_cons.ny2020_test.screens.ScreenType;
import com.vova_cons.ny2020_test.screens.game.GameScreen;
import com.vova_cons.ny2020_test.services.ScreensService;
import com.vova_cons.ny2020_test.services.ServiceLocator;

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
                    int y = height - line - 1;
                    if (tiles[x] == '@') {
                        world.playerX = x;
                        world.playerY = y;
                    } else if (tiles[x] == 'S') {
                        GameWorld.Enemy enemy = new GameWorld.Enemy();
                        enemy.x = x;
                        enemy.y = y;
                        enemy.type = GameWorld.EnemyType.Slime;
                        world.enemies.add(enemy);
                    } else if (tiles[x] == 'F') {
                        GameWorld.Enemy enemy = new GameWorld.Enemy();
                        enemy.x = x;
                        enemy.y = y;
                        enemy.type = GameWorld.EnemyType.Fly;
                        world.enemies.add(enemy);
                    } else {
                        int tile = TileType.detectTile(tiles[x]);
                        world.level.set(x, y, tile);
                    }
                }
            }
            return world;
        } catch (Exception e) {
            GameScreen.LEVEL = 1;
            new RuntimeException("Error with parsing level " + levelPath, e).printStackTrace();
            ScreensService screensService = ServiceLocator.getService(ScreensService.class);
            screensService.changeScreen(ScreenType.MenuScreen);
            return null;
        }
    }
}
