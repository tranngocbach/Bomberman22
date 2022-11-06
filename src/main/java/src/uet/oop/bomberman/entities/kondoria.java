package src.uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import java.util.*;

import javafx.util.Pair;
import src.uet.oop.bomberman.BombermanGame;
import src.uet.oop.bomberman.audio.MyAudioPlayer;
import src.uet.oop.bomberman.graphics.Sprite;

public class kondoria extends Entity {
    List<Entity> entities;
    private int speed = Sprite.SCALED_SIZE / 32;
    int playerX;
    int playerY;

    int currentDirection ;
    List<Pair<Integer,Integer> > listMove = new ArrayList<>();
    public kondoria(int x, int y, Image img) {
        super(x, y, img);
        passable = true;
        listMove.add(new Pair<>(0,-1));
        listMove.add(new Pair<>(-1,0));
        listMove.add(new Pair<>(0,1));
        listMove.add(new Pair<>(1,0));
    }

    public boolean moveRight() {
        setImg(Sprite.movingSprite(Sprite.kondoria_right1
                , Sprite.kondoria_right2
                , Sprite.kondoria_right3
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
        setImg(Sprite.movingSprite(Sprite.kondoria_left1
                , Sprite.kondoria_left2
                , Sprite.kondoria_left3
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
        setImg(Sprite.movingSprite(Sprite.kondoria_right1
                , Sprite.kondoria_right2
                , Sprite.kondoria_right3
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
        setImg(Sprite.movingSprite(Sprite.kondoria_left1
                , Sprite.kondoria_left2
                , Sprite.kondoria_left3
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
                if(BombermanGame.entities.get(i) instanceof Brick) {
                    continue;
                }
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
                currentDirection = generator.nextInt(4);
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
