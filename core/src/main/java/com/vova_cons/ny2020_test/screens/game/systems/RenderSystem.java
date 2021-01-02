package com.vova_cons.ny2020_test.screens.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.vova_cons.ny2020_test.screens.game.components.BodyComponent;
import com.vova_cons.ny2020_test.screens.game.components.SpriteComponent;
import com.vova_cons.ny2020_test.screens.game.utils.Families;
import com.vova_cons.ny2020_test.screens.game.utils.Mappers;
import com.vova_cons.ny2020_test.screens.game.world.GameWorld;
import com.vova_cons.ny2020_test.screens.game.world.TileType;

public class RenderSystem extends SortedIteratingSystem {
    private static final float TILE_SIZE = 75;
    private final GameWorld world;
    private final Batch batch;
    private ObjectMap<SpriteComponent.Type, Texture> textures = new ObjectMap<>();
    private IntMap<Texture> tiles = new IntMap<>();

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
        tiles.put(TileType.GROUND, new Texture("mario/png/ground.png"));
        tiles.put(TileType.TRAP, new Texture("mario/png/spikes.png"));
    }

    private Texture createTextureFor(SpriteComponent.Type textureType) {
        switch(textureType) {
            case PlayerIdle:
                return new Texture("mario/png/character/front.png");
        }
        return null;
    }

    private static int compare(Entity e1, Entity e2) {
        return (int)Math.signum(Mappers.sprite.get(e1).z - Mappers.sprite.get(e2).z);
    }

    @Override
    public void update(float deltaTime) {
        batch.begin();
        drawWorld();
        // render entities by z index
        super.update(deltaTime);
        batch.flush();
        batch.end();
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
            batch.draw(texture, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
    }

    protected void processEntity(Entity entity, float deltaTime) {
        SpriteComponent sprite = Mappers.sprite.get(entity);
        BodyComponent body = Mappers.body.get(entity);
        Texture texture = textures.get(sprite.type);
        batch.draw(texture, body.x * TILE_SIZE, body.y * TILE_SIZE,
                body.w * TILE_SIZE, body.h * TILE_SIZE);
    }
}
