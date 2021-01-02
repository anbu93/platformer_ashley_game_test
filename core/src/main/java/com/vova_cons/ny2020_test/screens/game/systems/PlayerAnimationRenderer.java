package com.vova_cons.ny2020_test.screens.game.systems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.vova_cons.ny2020_test.screens.game.components.AnimationComponent;
import com.vova_cons.ny2020_test.screens.game.components.BodyComponent;
import com.vova_cons.ny2020_test.utils.MathUtils;

import static com.vova_cons.ny2020_test.screens.game.systems.RenderSystem.TILE_SIZE;

public class PlayerAnimationRenderer {
    private final Texture idle;
    private final Texture jump;
    private Animation<Texture> walkAnimation;
    private Rectangle drawRect = new Rectangle();

    public PlayerAnimationRenderer() {
        // fuck this artists! all assets not equal sizes!!!11 D:
        // 66x92 = 0.71739
        idle = new Texture("mario/png/character/side.png");
        // 68x94 = 0.72340
        jump = new Texture("mario/png/character/jump.png");
        // 75x96 = 0.78125
        walkAnimation = new Animation<Texture>(1f/ 60f,
                new Texture("mario/png/character/walk/walk0001.png"),
                new Texture("mario/png/character/walk/walk0002.png"),
                new Texture("mario/png/character/walk/walk0003.png"),
                new Texture("mario/png/character/walk/walk0004.png"),
                new Texture("mario/png/character/walk/walk0005.png"),
                new Texture("mario/png/character/walk/walk0006.png"),
                new Texture("mario/png/character/walk/walk0007.png"),
                new Texture("mario/png/character/walk/walk0008.png"),
                new Texture("mario/png/character/walk/walk0009.png"),
                new Texture("mario/png/character/walk/walk0010.png"),
                new Texture("mario/png/character/walk/walk0011.png"));
    }

    public void render(Batch batch, float cameraX, float cameraY, BodyComponent body, AnimationComponent animation) {
        Texture texture = null;
        switch(animation.type) {
            case Idle:
                drawRect.set(0, 0, 66, 92);
                texture = idle;
                break;
            case Walk:
                drawRect.set(0, 0, 68, 94);
                texture = walkAnimation.getKeyFrame(animation.time, true);
                break;
            case Jump:
                drawRect.set(0, 0, 75, 96);
                 texture = jump;
                break;
        }
        if (texture != null) {
            drawRect = MathUtils.fitOut(drawRect, body.w * TILE_SIZE, body.h * TILE_SIZE);
            if (animation.direction == 1) {
                batch.draw(texture, -cameraX + body.x * TILE_SIZE + drawRect.x,
                        -cameraY + body.y * TILE_SIZE + drawRect.y,
                        drawRect.width, drawRect.height);
            } else {
                batch.draw(texture, -cameraX + body.x * TILE_SIZE + drawRect.x,
                        -cameraY + body.y * TILE_SIZE + drawRect.y,
                        drawRect.width, drawRect.height, 0, 0,
                        texture.getWidth(), texture.getHeight(), true, false);
            }
        }
    }
}
