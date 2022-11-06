package src.uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import src.uet.oop.bomberman.BombermanGame;
import src.uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {
    boolean isLast;

    int endAnimation = 100;
    int direction;

    //  direction
    //     1
    //   0   2
    //     3

    public Flame() {
        super();
    }

    public Flame(int xUnit, int yUnit, boolean isLast, int direction, Image img) {
        super(xUnit, yUnit, img);
        this.passable = true;
        this.isLast = isLast;
        this.direction = direction;
    }

    @Override
    public void update() {

        for (int i = BombermanGame.enemies.size() - 1; i >= 0; i--) {
            if (this.intersects(BombermanGame.enemies.get(i))){
                BombermanGame.enemies.get(i).updateStatus();
            }
        }
        if (this.intersects(BombermanGame.bomberman)){
            BombermanGame.bomberman.updateStatus();
        }
        if (direction == 0) {
            if (isLast) {
                setImg(Sprite.movingSprite(Sprite.explosion_horizontal_left_last
                        , Sprite.explosion_horizontal_left_last1
                        , Sprite.explosion_horizontal_left_last2
                        , animate
                        , endAnimation).getFxImage());
            } else {
                setImg(Sprite.movingSprite(Sprite.explosion_horizontal
                        , Sprite.explosion_horizontal1
                        , Sprite.explosion_horizontal2
                        , animate
                        , endAnimation).getFxImage());
            }
        }

        if (direction == 1) {
            if (isLast) {
                setImg(Sprite.movingSprite(Sprite.explosion_vertical_top_last
                        , Sprite.explosion_vertical_top_last1
                        , Sprite.explosion_vertical_top_last2
                        , animate
                        , endAnimation).getFxImage());
            } else {
                setImg(Sprite.movingSprite(Sprite.explosion_vertical
                        , Sprite.explosion_vertical1
                        , Sprite.explosion_vertical2
                        , animate
                        , endAnimation).getFxImage());
            }
        }

        if (direction == 2) {
            if (isLast) {
                setImg(Sprite.movingSprite(Sprite.explosion_horizontal_right_last
                        , Sprite.explosion_horizontal_right_last1
                        , Sprite.explosion_horizontal_right_last2
                        , animate
                        , endAnimation).getFxImage());
            } else {
                setImg(Sprite.movingSprite(Sprite.explosion_horizontal
                        , Sprite.explosion_horizontal1
                        , Sprite.explosion_horizontal2
                        , animate
                        , endAnimation).getFxImage());
            }
        }

        if (direction == 3) {
            if (isLast) {
                setImg(Sprite.movingSprite(Sprite.explosion_vertical_down_last
                        , Sprite.explosion_vertical_down_last1
                        , Sprite.explosion_vertical_down_last2
                        , animate
                        , endAnimation).getFxImage());
            } else {
                setImg(Sprite.movingSprite(Sprite.explosion_vertical
                        , Sprite.explosion_vertical1
                        , Sprite.explosion_vertical2
                        , animate
                        , endAnimation).getFxImage());
            }
        }

        animate += 1;
        if (animate == 25) {
            this.appear = false;
        }
    }
}
