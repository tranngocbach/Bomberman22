package src.uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Bomber extends Entity {

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    public void goDown() {
        y ++;
    }

    public void goUp() {
        y --;
    }

    public void goRight() {
        x ++;
    }

    public void goLeft() {
        x --;
    }

    @Override
    public void update() {

    }
}
