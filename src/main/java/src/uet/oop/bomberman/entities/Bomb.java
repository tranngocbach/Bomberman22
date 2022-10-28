package src.uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import src.uet.oop.bomberman.BombermanGame;
import src.uet.oop.bomberman.audio.MyAudioPlayer;
import src.uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity{
    int distance;

    public Bomb(int xUnit, int yUnit, int distance, Image img) {
        super(xUnit, yUnit, img);
        this.distance = distance;
    }

    public List<Query> explode(char[][] map, Entity[][] mapToId, int direction) {
        List<Query> l = new ArrayList<>();
        int x = this.getX(), y = this.getY(), i = 0;
        do {
            Flame f = new Flame();
            switch (direction) {
                case 0: {
                    x--;
                    f = new Flame(x, y, false, 0, Sprite.explosion_horizontal.getFxImage());
                    break;
                }
                case 1: {
                    y--;
                    f = new Flame(x, y, false, 1, Sprite.explosion_vertical.getFxImage());
                    break;
                }
                case 2: {
                    x++;
                    f = new Flame(x, y, false, 2, Sprite.explosion_horizontal.getFxImage());
                    break;
                }
                case 3: {
                    y++;
                    f = new Flame(x, y, false, 3, Sprite.explosion_vertical.getFxImage());
                    break;
                }
            }
            if(i == distance - 1){
                break;
            }
            if(mapToId[y][x] instanceof Bomber || mapToId[y][x] instanceof Oneal){
                mapToId[y][x].updateStatus();
            }
            if (mapToId[y][x] == null || mapToId[y][x].canPass()) {
                l.add(new Query("add", f));
            } else {
                break;
            }
        } while (++i < distance);

        if (mapToId[y][x] == null || mapToId[y][x].canPass()) {
            Flame f = new Flame();
            switch (direction) {
                case 0: {
                    f = new Flame(x, y, true, 0, Sprite.explosion_horizontal_left_last.getFxImage());
                    break;
                }
                case 1: {
                    f = new Flame(x, y, true, 1, Sprite.explosion_vertical_top_last.getFxImage());
                    break;
                }
                case 2: {
                    f = new Flame(x, y, true, 2, Sprite.explosion_horizontal_right_last.getFxImage());
                    break;
                }
                case 3: {
                    f = new Flame(x, y, true, 3, Sprite.explosion_vertical_down_last.getFxImage());
                    break;
                }
            }
            l.add(new Query("add", f));

        }
        if(mapToId[y][x] instanceof Brick){
            mapToId[y][x].updateStatus();
            map[y][x] = ' ';
            mapToId[y][x] = null;
        }

        return l;
    }

    public List<Query> bombExplode(char[][] map,Entity[][] mapToId) {
        List<Query> l = new ArrayList<>();
        l.addAll(explode(map,mapToId, 0));
        l.addAll(explode(map, mapToId, 1));
        l.addAll(explode(map, mapToId, 2));
        l.addAll(explode(map, mapToId, 3));
        return l;
    }

    @Override
    public void update(Entity[][] mapToId) {
        if(status == 0) {
            setImg(Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 30).getFxImage());
            animate += 1;
            if (animate == 100) {
                this.status = 1;
                this.animate = 0;
            }
        } else {
            if(mapToId[this.getY()][this.getX()] instanceof Bomber){
                mapToId[this.getY()][this.getX()].updateStatus();
            }
            if (animate == 0) {
                MyAudioPlayer explodesound = new MyAudioPlayer(MyAudioPlayer.EXPLOSION);
                explodesound.play();
            }
            setImg(Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, animate, 30).getFxImage());
            animate += 1;
            if (animate == 50) {
                BombermanGame.numberOfBombs ++;
                this.appear = false;
                mapToId[this.getY()][this.getX()] = null;
            }
        }

    }


}
