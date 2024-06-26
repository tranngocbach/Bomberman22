package src.uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import src.uet.oop.bomberman.BombermanGame;

public class SpeedItem extends Entity {
    public SpeedItem(int x, int y, Image img) {
        super(x, y, img);
        this.passable = true;
    }

    @Override
    public void update() {
        if (this.intersects(BombermanGame.bomberman)){
            BombermanGame.bomberman.speed *= 2;
            /*BombermanGame.bomberman.setX(this.getX() * 32);
            BombermanGame.bomberman.setY(this.getY() * 32);*/
            this.destroy();
        }
    }
}
