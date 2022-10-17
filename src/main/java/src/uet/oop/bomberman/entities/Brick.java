package src.uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import src.uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        destroyable = true;
        passable = false;
    }

    @Override
    public void update(Entity[][] mapToId) {
        if(status == 0){
            return;
        }
        setImg(Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, animate, 50).getFxImage());
        animate += 1;
        if(animate == 50) {
            this.appear = false;
        }
    }
}
