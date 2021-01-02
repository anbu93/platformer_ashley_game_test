package com.vova_cons.ny2020_test.screens.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.vova_cons.ny2020_test.screens.game.components.AnimationComponent;
import com.vova_cons.ny2020_test.screens.game.components.BodyComponent;
import com.vova_cons.ny2020_test.screens.game.components.CameraComponent;
import com.vova_cons.ny2020_test.screens.game.components.SpriteComponent;
import com.vova_cons.ny2020_test.screens.game.utils.Families;
import com.vova_cons.ny2020_test.screens.game.utils.Mappers;
import com.vova_cons.ny2020_test.screens.game.world.GameWorld;
import com.vova_cons.ny2020_test.screens.game.world.TileType;

public class RenderSystem extends SortedIteratingSystem {
    public static final float TILE_SIZE = 75;
    private final GameWorld world;
    private final Batch batch;
    private ObjectMap<SpriteComponent.Type, Texture> textures = new ObjectMap<>();
    private ImmutableArray<Entity> cameras;
    private float cameraX, cameraY;
    private IntMap<Texture> tiles = new IntMap<>();
    private PlayerAnimationRenderer playerRenderer = new PlayerAnimationRenderer();

    public RenderSystem(GameWorld world, Batch batch) {
        this(world, batch, 0);
    }

    public RenderSystem(GameWorld world, Batch batch, int priority) {
        super(Families.drawing, RenderSystem::compare, priority);
        this.world = world;
        this.batch = batch;
        for(SpriteComponent.Type textureType : SpriteComponent.Type.values()) {
            textures.put(textureType, createTextureFor(textureType));
        }
        tiles.put(TileType.GROUND, new Texture("mario/png/ground_dirt.png"));
        tiles.put(TileType.GROUND_GRASS, new Texture("mario/png/ground.png"));
        tiles.put(TileType.TRAP, new Texture("mario/png/spikes.png"));
        tiles.put(TileType.LAVA, new Texture("mario/png/lava.png"));
        tiles.put(TileType.BUTTON_RED, new Texture("mario/png/switch_red_off.png"));
        tiles.put(TileType.BUTTON_RED_ACTIVE, new Texture("mario/png/switch_red_on.png"));
        tiles.put(TileType.COIN_GOLD, new Texture("mario/png/coin_gold.png"));
        tiles.put(TileType.COIN_SILVER, new Texture("mario/png/coin_silver.png"));
    }

    private Texture createTextureFor(SpriteComponent.Type textureType) {
        switch(textureType) {
            case PlayerIdle:
                return new Texture("mario/png/character/front.png");
            case Slime:
                return new Texture("mario/png/enemies/slime_walk.png");
            case Fly:
                return new Texture("mario/png/enemies/fly_fly.png");
        }
        return null;
    }

    private static int compare(Entity e1, Entity e2) {
        SpriteComponent s1 = Mappers.sprite.get(e1);
        SpriteComponent s2 = Mappers.sprite.get(e2);
        if (s1 != null && s2 != null) {
            return (int) Math.signum(s1.z - s2.z);
        }
        return -1;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        cameras = engine.getEntitiesFor(Families.camera);
    }

    @Override
    public void update(float deltaTime) {
        batch.begin();
        detectCamera();
        drawWorld();
        // render entities by z index
        super.update(deltaTime);
        batch.flush();
        batch.end();
    }

    private void detectCamera() {
        for(Entity entity : cameras) {
            CameraComponent camera = Mappers.camera.get(entity);
            cameraX = camera.x * TILE_SIZE;
            cameraY = camera.y * TILE_SIZE;
            return;
        }
    }

    private void drawWorld() {
        world.level.foreach(this::drawTile);
    }

    private void drawTile(int x, int y, int tile) {
        if (tile == TileType.EMPTY) {
            return;
        }
        Texture texture = tiles.get(tile, null);
        if (texture != null) {
            batch.draw(texture, -cameraX + x * TILE_SIZE,
                    -cameraY + y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
    }

    protected void processEntity(Entity entity, float deltaTime) {
        BodyComponent body = Mappers.body.get(entity);
        SpriteComponent sprite = Mappers.sprite.get(entity);
        if (sprite != null) {
            Texture texture = textures.get(sprite.type);
            batch.draw(texture, -cameraX + body.x * TILE_SIZE,
                    -cameraY + body.y * TILE_SIZE,
                    body.w * TILE_SIZE, body.h * TILE_SIZE);
        } else { // TODO fix after
            AnimationComponent animation = Mappers.animation.get(entity);
            playerRenderer.render(batch, cameraX, cameraY, body, animation);
        }
    }
}
