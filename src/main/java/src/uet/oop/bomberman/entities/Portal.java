package src.uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Portal extends Entity{
    Portal outPortal;
    public Portal(int x, int y, Image img) {
        super(x, y, img);
        this.passable = true;
    }

    @Override
    public void update(Entity[][] mapToId) {

    }
}