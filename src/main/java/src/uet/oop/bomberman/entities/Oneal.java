package src.uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import java.util.*;

import javafx.util.Pair;
import src.uet.oop.bomberman.BombermanGame;
import src.uet.oop.bomberman.audio.MyAudioPlayer;
import src.uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Entity {
    List<Entity> entities;
    private int speed = Sprite.SCALED_SIZE / 32;

    int currentDirection ;
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        passable = true;
    }

    public boolean moveRight() {
        setImg(Sprite.movingSprite(Sprite.oneal_right1
                , Sprite.oneal_right2
                , Sprite.oneal_right3
                , animate
                , 36).getFxImage());
        this.x = this.x + speed;
        if (!this.checkIntersect()) {
            this.x -= speed;
            return false;
        }
        return true;
    }

    public boolean moveLeft() {
        setImg(Sprite.movingSprite(Sprite.oneal_left1
                , Sprite.oneal_left2
                , Sprite.oneal_left3
                , animate
                , 36).getFxImage());
        this.x = this.x - speed;
        if (!this.checkIntersect()) {
            this.x += speed;
            return false;
        }
        return true;
    }

    public boolean moveUp() {
        setImg(Sprite.movingSprite(Sprite.oneal_right1
                , Sprite.oneal_right2
                , Sprite.oneal_right3
                , animate
                , 36).getFxImage());

        this.y = this.y - speed;
        if (!this.checkIntersect()) {
            this.y += speed;
            return false;
        }
        return true;
    }

    public boolean moveDown() {
        setImg(Sprite.movingSprite(Sprite.oneal_left1
                , Sprite.oneal_left2
                , Sprite.oneal_left3
                , animate
                , 36).getFxImage());

        this.y = this.y + speed;
        if (!this.checkIntersect()) {
            this.y -= speed;
            return false;
        }
        return true;
    }
    public boolean checkIntersect() {
        if (!this.checkAppearance()) {
            return false;
        }
        for (int i = BombermanGame.entities.size() - 1; i >= 0; i--) {
            if (this.intersects(BombermanGame.entities.get(i)) && !BombermanGame.entities.get(i).canPass()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void update() {
        if(status == 0) {
            animate++;
            Random generator = new Random();

            if (this.inCell()) {
                int nextDirection = new BFS(BombermanGame.mapMatrix
                        ,this.getX()
                        ,this.getY()
                        ,BombermanGame.bomberman.getX()
                        ,BombermanGame.bomberman.getY()).nextDirection;

                if(nextDirection != -1)
                {
                    speed = Sprite.SCALED_SIZE / 16;
                    currentDirection = nextDirection;
                }
                else
                {
                    speed = Sprite.SCALED_SIZE / 32;
                    currentDirection = generator.nextInt(4);
                }
            }
            switch (currentDirection) {
                case 0: {
                    this.moveLeft();
                    break;
                }
                case 1: {
                    this.moveUp();
                    break;
                }
                case 2: {
                    this.moveRight();
                    break;
                }
                case 3: {
                    this.moveDown();
                    break;
                }
            }
        }

        if(status == 1){
            if (animate == 0) {
                MyAudioPlayer deadOneal = new MyAudioPlayer(MyAudioPlayer.ENEMY_DEAD);
                deadOneal.play();
            }
            setImg(Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 150).getFxImage());
            animate += 1;
            if (animate == 150) {
                this.appear = false;
            }
        }
    }
}
