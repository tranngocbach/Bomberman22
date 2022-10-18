package src.uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class SpeedItem extends Entity {
    public SpeedItem(int x, int y, Image img) {
        super(x, y, img);
        this.passable = true;
    }

    @Override
    public void update(Entity[][] mapToId) {
        mapToId[this.getY()][this.getX()] = this;
    }
}
