package src.uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import src.uet.oop.bomberman.graphics.Sprite;
import java.util.List;
import java.util.ArrayList;

public class Bomber extends Entity {
    private int speed = Sprite.SCALED_SIZE / 4;

    private boolean chuaRaKhoiBomb = false;

    private int explodeDistance ;
    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        this.previousX = x;
        this.previousY = y;
        this.explodeDistance = 100;
        this.passable = true;
    }

    private boolean roundByXAndMove(List<Entity> entities) {
        if(checkIntersect(entities)) {
            return true;
        }
        for(int dif = -16;dif <= 16; dif += 4){
            this.x += dif;
            if(checkIntersect(entities)) {
                return true;
            }
            this.x -= dif;
        }
        return false;
    }

    private boolean roundByYAndMove(List<Entity> entities) {
        if(checkIntersect(entities)) {
            return true;
        }
        for(int dif = -16;dif <= 16; dif += 4){
            this.y += dif;
            if(checkIntersect(entities)) {
                return true;
            }
            this.y -= dif;
        }
        return false;
    }
    public void moveRight(List<Entity> entities, Entity mapToId[][]) {
        setImg(Sprite.movingSprite(Sprite.player_right
                , Sprite.player_right_1
                , Sprite.player_right_2
                , animate
                , 36).getFxImage());
        this.x = this.x + speed;
        if(!roundByYAndMove(entities)){
           this.x -= speed;
        }
    }

    public void moveLeft(List<Entity> entities, Entity mapToId[][]) {
        setImg(Sprite.movingSprite(Sprite.player_left
                , Sprite.player_left_1
                , Sprite.player_left_2
                , animate
                , 36).getFxImage());
        this.x = this.x - speed;
        if(!roundByYAndMove(entities)){
            this.x += speed;
        }

    }

    public void moveUp(List<Entity> entities, Entity mapToId[][]) {
        setImg(Sprite.movingSprite(Sprite.player_up
                , Sprite.player_up_1
                , Sprite.player_up_2
                , animate
                , 36).getFxImage());

        this.y = this.y - speed;
        if(!roundByXAndMove(entities)){
            this.y += speed;
        }

    }

    public void moveDown(List<Entity> entities, Entity mapToId[][]) {
        setImg(Sprite.movingSprite(Sprite.player_down
                , Sprite.player_down_1
                , Sprite.player_down_2
                , animate
                , 36).getFxImage());

        this.y = this.y + speed;
        if(!roundByXAndMove(entities)){
            this.y -= speed;
        }
    }

    private boolean checkIntersect(List<Entity> entities) {
        if(!this.checkAppearance()) {
            return false;
        }
        boolean flag = true;
        for(int i = entities.size() - 1; i >= 0 ; i--) {
            if(this.intersects(entities.get(i)) && entities.get(i) instanceof Bomb) {
                if(this.chuaRaKhoiBomb) {
                    flag = false;
                    continue;
                }
                else {
                    return false;
                }
            }
            if(this.intersects(entities.get(i)) && !entities.get(i).canPass()){
                return false;
            }
        }
        if(flag){
            this.chuaRaKhoiBomb = false;
        }
        return true;
    }
    public Bomb placeBomb(){
        chuaRaKhoiBomb = true;
        return new Bomb(this.x/Sprite.SCALED_SIZE, this.y/Sprite.SCALED_SIZE, explodeDistance, Sprite.bomb.getFxImage());
    }

    public List<Query> powerUp(char map[][],Entity mapToId[][]){
        List<Query> l = new ArrayList<>();
        int y = this.getY();
        int x = this.getX();
        if(map[y][x] == 'f'){
            l.add(new Query("remove",mapToId[this.getY()][this.getX()]));
            map[y][x] = ' ';
            mapToId[this.getY()][this.getX()] = null;
            this.explodeDistance++;
        }
        return l;
    }
    @Override
    public void update(Entity[][] mapToId) {
        animate ++;
        if(status == 0){
            if(animate == 36){animate = 0;}
            if(mapToId[this.getY()][this.getX()] instanceof Oneal){
                this.updateStatus();
            }
            mapToId[previousY][previousX] = null;
            mapToId[this.getY()][this.getX()] = this;
            previousX = this.getX();
            previousY = this.getY();
        }
        if(status == 1){
            setImg(Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animate, 150).getFxImage());
            if (animate == 150) {
                this.appear = false;
                mapToId[this.getY()][this.getX()] = null;
            }
        }
    }
}
