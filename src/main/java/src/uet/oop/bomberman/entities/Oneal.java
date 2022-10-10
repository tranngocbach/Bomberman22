package src.uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import java.util.*;

import javafx.util.Pair;
import src.uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Entity {
    private int speed = Sprite.SCALED_SIZE;
    int playerX;
    int playerY;
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
        for(int i = 0 ; i < 4; i++){
            int x = Math.abs(this.x + listMove.get(i).getKey() - playerX);
            int y = Math.abs(this.y + listMove.get(i).getValue() - playerY);
            min = Math.min(min, x + y);
        }
        for(int i = 0 ; i < 4; i++){
            int x = Math.abs(this.x + listMove.get(i).getKey() - playerX);
            int y = Math.abs(this.y + listMove.get(i).getValue() - playerY);
            if(x + y == min)
            {
                return i;
            }
        }
        return 0;
    }
    public void moveRight(Entity[][] mapToId) {
        this.x = this.x + speed;
        if (mapToId[this.getY()][this.getX()] != null) {
            this.x -= speed;
            return;
        }
        setImg(Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, this.getX(), 80).getFxImage());

    }

    public void moveLeft(Entity[][] mapToId) {
        this.x = this.x - speed;
        if (mapToId[this.getY()][this.getX()] != null) {
            this.x += speed;
            return;
        }
        setImg(Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, this.getX(), 80).getFxImage());
    }

    public void moveUp(Entity[][] mapToId) {
        this.y = this.y - speed;
        if (mapToId[this.getY()][this.getX()] != null) {
            this.y += speed;
            return;
        }
        setImg(Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, this.getX(), 80).getFxImage());
    }

    public void moveDown(Entity[][] mapToId) {
        this.y = this.y + speed;
        if (mapToId[this.getY()][this.getX()] != null) {
            this.y -= speed;
            return;
        }
        setImg(Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, this.getX(), 80).getFxImage());
    }

    public void getPlayerInfo(int x,int y) {
        this.playerX = x;
        this.playerY = y;
    }

    @Override
    public void update(Entity[][] mapToId) {
        if(status == 0){
            Random generator = new Random();

            if(mapToId[this.getY()][this.getX()] instanceof Bomber){
                mapToId[this.getY()][this.getX()].updateStatus();
            }
            mapToId[this.getY()][this.getX()] = this;
            if(this.getX() != previousX || this.getY() != previousY){
                mapToId[previousY][previousX] = null;
            }
            previousX = this.getX();
            previousY = this.getY();

            animate++;
            int di = generator.nextInt(4);
            if(animate % 15 == 0) {
                switch (di) {
                    case 0: {
                        this.moveLeft(mapToId);
                        break;
                    }
                    case 1: {
                        this.moveUp(mapToId);
                        break;
                    }
                    case 2: {
                        this.moveRight(mapToId);
                        break;
                    }
                    case 3: {
                        this.moveDown(mapToId);
                        break;
                    }
                }
            }
            /*if(animate / 500 % 4 == 0){
                switch (di){
                    case 0:{this.moveLeft(mapToId);break;}
                    case 1:{this.moveUp(mapToId);break;}
                    //case 2:{this.moveRight(mapToId);break;}
                    //case 3:{this.moveDown(mapToId);break;}
                }
            } else if (animate / 500 % 4 == 1) {
                switch (di){
                    case 0:{this.moveRight(mapToId);break;}
                    case 1:{this.moveUp(mapToId);break;}
                    }
            } else if (animate / 500 % 4 == 2) {
                switch (di){
                    case 0:{this.moveRight(mapToId);break;}
                    case 1:{this.moveDown(mapToId);break;}
                }
            } else if (animate / 500 % 4 == 3) {
                switch (di){
                    case 0:{this.moveLeft(mapToId);break;}
                    case 1:{this.moveDown(mapToId);break;}
                }
            }*/
        }
        if(status == 1){
            setImg(Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 150).getFxImage());
            animate += 1;
            if (animate == 150) {
                this.appear = false;
            }
        }
    }
}
