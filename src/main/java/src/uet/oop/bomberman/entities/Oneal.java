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
    List<Pair<Integer,Integer> > listMove = new ArrayList<>();
    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        previousX = x;
        previousY = y;
        passable = true;
        listMove.add(new Pair<>(0,-1));
        listMove.add(new Pair<>(-1,0));
        listMove.add(new Pair<>(0,1));
        listMove.add(new Pair<>(1,0));
    }

    int findGoodPathToPlayer() {
        int min = Integer.MAX_VALUE;
        int playerX = BombermanGame.bomberman.getX();
        int playerY = BombermanGame.bomberman.getY();
        for(int i = 0 ; i < 4; i++){
            int x = Math.abs(this.getX() + listMove.get(i).getKey() - playerX);
            int y = Math.abs(this.getY() + listMove.get(i).getValue() - playerY);
            min = Math.min(min, x + y);
        }
        for(int i = 0 ; i < 4; i++){
            int x = Math.abs(this.getX() + listMove.get(i).getKey() - playerX);
            int y = Math.abs(this.getY() + listMove.get(i).getValue() - playerY);
            if(x + y == min)
            {
                return i;
            }
        }
        return 0;
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
                currentDirection = findGoodPathToPlayer();
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
                MyAudioPlayer deadAudio = new MyAudioPlayer(MyAudioPlayer.DEAD);
                deadAudio.play();
            }
            setImg(Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 150).getFxImage());
            animate += 1;
            if (animate == 150) {
                this.appear = false;
            }
        }
    }
}
