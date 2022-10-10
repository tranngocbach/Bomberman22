package src.uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import src.uet.oop.bomberman.graphics.Sprite;
import java.util.List;
import java.util.ArrayList;

public class Bomber extends Entity {
    private int speed = Sprite.SCALED_SIZE;

    private int explodeDistance ;
    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        this.previousX = x;
        this.previousY = y;
        this.explodeDistance = 2;
        this.passable = true;
    }

    public int getExplodeDistance(){
        return explodeDistance;
    }
    public void setExplodeDistance(int e){
        this.explodeDistance = e;
    }
    public void moveRight(List<Entity> entities, Entity mapToId[][]) {
        this.x = this.x + speed;
        if (!this.checkIntersect(entities)) {
            this.x -= speed;
            return;
        }
        setImg(Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, this.getX(), 80).getFxImage());

    }

    public void moveLeft(List<Entity> entities, Entity mapToId[][]) {
        this.x = this.x - speed;
        if (!this.checkIntersect(entities)) {
            this.x += speed;
            return;
        }
        setImg(Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, this.getX(), 80).getFxImage());
    }

    public void moveUp(List<Entity> entities, Entity mapToId[][]) {
        this.y = this.y - speed;
        if (!this.checkIntersect(entities)) {
            this.y += speed;
            return;
        }
        setImg(Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, this.getY(), 80).getFxImage());
    }

    public void moveDown(List<Entity> entities, Entity mapToId[][]) {
        this.y = this.y + speed;
        if (!this.checkIntersect(entities)) {
            this.y -= speed;
            return;
        }
        setImg(Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, this.getY(), 80).getFxImage());
    }

    public boolean checkIntersect(List<Entity> entities) {
        int block = 0;
        for(int i = entities.size() - 1; i >= 0 ; i--) {
            if(this.intersects(entities.get(i)) && !entities.get(i).canPass()){
                return false;
            }
        }
        return true;
    }
    public Bomb placeBomb(){
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
        if(mapToId[this.getY()][this.getX()] instanceof Oneal){
            this.updateStatus();
        }
        mapToId[this.getY()][this.getX()] = this;
        if(this.getX() != previousX || this.getY() != previousY){
            mapToId[previousY][previousX] = null;
        }
        previousX = this.getX();
        previousY = this.getY();
        if(status == 1){
            setImg(Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animate, 150).getFxImage());
            animate += 1;
            if (animate == 150) {
                this.appear = false;
            }
        }
    }
}
