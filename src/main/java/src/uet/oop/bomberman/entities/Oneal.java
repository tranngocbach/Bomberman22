package src.uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import java.util.*;

import javafx.util.Pair;
import src.uet.oop.bomberman.audio.MyAudioPlayer;
import src.uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Entity {
    List<Entity> entities;
    private int speed = Sprite.SCALED_SIZE / 32;
    int playerX;
    int playerY;

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
    public boolean moveRight(List<Entity> entities) {
        setImg(Sprite.movingSprite(Sprite.oneal_right1
                , Sprite.oneal_right2
                , Sprite.oneal_right3
                , animate
                , 36).getFxImage());
        this.x = this.x + speed;
        if (!this.checkIntersect(entities)) {
            this.x -= speed;
            return false;
        }
        return true;
    }

    public boolean moveLeft(List<Entity> entities) {
        setImg(Sprite.movingSprite(Sprite.oneal_left1
                , Sprite.oneal_left2
                , Sprite.oneal_left3
                , animate
                , 36).getFxImage());
        this.x = this.x - speed;
        if (!this.checkIntersect(entities)) {
            this.x += speed;
            return false;
        }
        return true;
    }

    public boolean moveUp(List<Entity> entities) {
        setImg(Sprite.movingSprite(Sprite.oneal_right1
                , Sprite.oneal_right2
                , Sprite.oneal_right3
                , animate
                , 36).getFxImage());

        this.y = this.y - speed;
        if (!this.checkIntersect(entities)) {
            this.y += speed;
            return false;
        }
        return true;
    }

    public boolean moveDown(List<Entity> entities) {
        setImg(Sprite.movingSprite(Sprite.oneal_left1
                , Sprite.oneal_left2
                , Sprite.oneal_left3
                , animate
                , 36).getFxImage());

        this.y = this.y + speed;
        if (!this.checkIntersect(entities)) {
            this.y -= speed;
            return false;
        }
        return true;
    }
    public boolean checkIntersect(List<Entity> entities) {
        if (!this.checkAppearance()) {
            return false;
        }
        for (int i = entities.size() - 1; i >= 0; i--) {
            if (this.intersects(entities.get(i)) && !entities.get(i).canPass()) {
                return false;
            }
        }
        return true;
    }
    public void getInfo(int x,int y,List<Entity> entities) {
        this.playerX = x;
        this.playerY = y;
        this.entities = entities;
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

            // di chuyen ve huong currentDirection neu co the di chuyen nhieu huong thi ngau nhien
            int flag = 0;

            switch (currentDirection){
                case 0:{
                    if(!moveLeft(entities)){
                        flag = 1;
                    }
                    break;
                }
                case 1:{
                    if(!moveUp(entities)){
                        flag = 1;
                    }
                    break;
                }
                case 2:{
                    if(!moveRight(entities)){
                        flag = 1;
                    }
                    break;
                }
                case 3: {
                    if (!moveDown(entities)) {
                        flag = 1;
                    }
                    break;
                }
            }

            int cnt = 0;
            while((flag == 1 || (this.x == this.getX() * 32 && this.y == this.getY() * 32) && cnt++ < 100)) {

                int di = generator.nextInt(4);
                currentDirection = di;
                boolean flag2 = false;
                switch (di) {
                    case 0: {
                        if(this.moveLeft(entities)) {
                            flag2 = true;
                        }
                        break;
                    }
                    case 1: {
                        if(this.moveUp(entities)) {
                            flag2 = true;
                        }
                        break;
                    }
                    case 2: {
                        if(this.moveRight(entities)) {
                            flag2 = true;
                        }
                        break;
                    }
                    case 3: {
                        if(this.moveDown(entities)) {
                            flag2 = true;
                        }
                        break;
                    }
                }
                if(flag2) {
                    break;
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
        if (animate == 0) {
            MyAudioPlayer deadOneal = new MyAudioPlayer(MyAudioPlayer.ENEMY_DEAD);
            deadOneal.play();
        }
        if(status == 1){
            setImg(Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 150).getFxImage());
            animate += 1;
            if (animate == 150) {
                mapToId[this.getY()][this.getX()] = null;
                this.appear = false;
            }
        }
    }
}
