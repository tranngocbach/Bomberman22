package src.uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import src.uet.oop.bomberman.graphics.Sprite;
import java.util.List;
import java.util.ArrayList;

public class Bomber extends Entity {
    private int speed = Sprite.SCALED_SIZE / 8;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }
    public static List<Integer> listEvent = new ArrayList<>();

    public void moveRight() {
        this.x = this.x + speed;
        setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, this.getX(), 80).getFxImage());

    }

    public void moveLeft() {
        this.x = this.x - speed;
        setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, this.getX(), 80).getFxImage());
    }

    public void moveUp() {
        this.y = this.y - speed;
        setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, this.getY(), 80).getFxImage());
    }

    public void moveDown(){
        this.y = this.y + speed;
        setImg(Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, this.getY(), 80).getFxImage());
    }

    @Override
    public void update() {

    }
}
