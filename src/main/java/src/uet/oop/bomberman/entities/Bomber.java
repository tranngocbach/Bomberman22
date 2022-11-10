package src.uet.oop.bomberman.entities;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import src.uet.oop.bomberman.audio.MyAudioPlayer;
import src.uet.oop.bomberman.graphics.Sprite;
import src.uet.oop.bomberman.BombermanGame;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Bomber extends Entity {
    private Canvas camera;
    public int speed = Sprite.SCALED_SIZE / 32;

    public static int numberOfBombs = 1;
    private boolean chuaRaKhoiBomb = false;

    public int explodeDistance;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        this.explodeDistance = 1;
        numberOfBombs = 1;
        this.passable = true;
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(x, y, 28, 28);
    }

    public boolean intersects(Entity e) {
        return e.getBoundary().intersects(this.getBoundary());
    }

    public void keyListen() {
        //System.out.println(pressedKeys.size());
        if (!BombermanGame.pressedKeys.isEmpty()) {
            for (Iterator<KeyCode> it = BombermanGame.pressedKeys.iterator(); it.hasNext(); ) {
                //System.out.println(it.next());
                switch (it.next()) {
                    case RIGHT: {
                        moveRight();
                        break;
                    }
                    case LEFT: {
                        moveLeft();
                        break;
                    }
                    case UP: {
                        moveUp();
                        break;
                    }
                    case DOWN: {
                        moveDown();
                        break;
                    }
                    case SPACE: {
                        if (numberOfBombs > 0) {
                            Bomb bomb = placeBomb();
                            int flag = 0;
                            for (int i = BombermanGame.entities.size() - 1; i >= 0; i--) {
                                if (BombermanGame.entities.get(i) instanceof Bomb && this.intersects(BombermanGame.entities.get(i))){
                                    flag = 1;
                                }
                            }
                            if(flag == 0) {
                                BombermanGame.entities.add(bomb);
                                numberOfBombs--;
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    private boolean roundByXAndMove() {
        if (checkIntersect()) {
            return true;
        }

        for (int dif = -4; dif <= 4; dif += 4) {
            this.x += dif;
            if (checkIntersect()) {
                return true;
            }
            this.x -= dif;
        }
        return false;
    }

    private boolean roundByYAndMove() {
        if (checkIntersect()) {
            return true;
        }
        for (int dif = -4; dif <= 4; dif += 4) {
            this.y += dif;
            if (checkIntersect()) {
                return true;
            }
            this.y -= dif;
        }
        return false;
    }

    public void moveRight() {
        setImg(Sprite.movingSprite(Sprite.player_right
                , Sprite.player_right_1
                , Sprite.player_right_2
                , animate
                , 36).getFxImage());
        this.x = this.x + speed;
        if (!roundByYAndMove()) {
            this.x -= speed;
        }
    }

    public void moveLeft() {
        setImg(Sprite.movingSprite(Sprite.player_left
                , Sprite.player_left_1
                , Sprite.player_left_2
                , animate
                , 36).getFxImage());
        this.x = this.x - speed;
        if (!roundByYAndMove()) {
            this.x += speed;
        }

    }

    public void moveUp() {
        setImg(Sprite.movingSprite(Sprite.player_up
                , Sprite.player_up_1
                , Sprite.player_up_2
                , animate
                , 36).getFxImage());

        this.y = this.y - speed;
        if (!roundByXAndMove()) {
            this.y += speed;
        }

    }

    public void moveDown() {
        setImg(Sprite.movingSprite(Sprite.player_down
                , Sprite.player_down_1
                , Sprite.player_down_2
                , animate
                , 36).getFxImage());

        this.y = this.y + speed;
        if (!roundByXAndMove()) {
            this.y -= speed;
        }
    }

    private boolean checkIntersect() {
        if (this.getStatus() == 1) {
            return false;
        }
        boolean flag = true;
        for (int i = BombermanGame.entities.size() - 1; i >= 0; i--) {
            if (this.intersects(BombermanGame.entities.get(i)) && BombermanGame.entities.get(i) instanceof Bomb) {
                if (this.chuaRaKhoiBomb) {
                    flag = false;
                    continue;
                } else {
                    return false;
                }
            }
            if (this.intersects(BombermanGame.entities.get(i)) && !BombermanGame.entities.get(i).canPass()) {
                return false;
            }
        }
        if (flag) {
            this.chuaRaKhoiBomb = false;
        }
        return true;
    }

    public Bomb placeBomb() {
        MyAudioPlayer placeSound = new MyAudioPlayer(MyAudioPlayer.PLACE_BOMB);
        placeSound.play();
        chuaRaKhoiBomb = true;
        return new Bomb(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE, explodeDistance, true,Sprite.bomb.getFxImage());
    }


    @Override
    public void update() {
        camera = BombermanGame.gccanvas;
        double limitRight = camera.getWidth() - (416 / 2) / BombermanGame.ZOOM;
        if (x + 12 > (416 / 2) / BombermanGame.ZOOM && x + 12 <= limitRight) {
            camera.setLayoutX(- (x + 12 - (416 / 2) / BombermanGame.ZOOM) * BombermanGame.ZOOM);
        } else if (x + 12 <= (416 / 2) / BombermanGame.ZOOM) {
            camera.setLayoutX(0);
        } else {
            camera.setLayoutX((BombermanGame.WIDTH - camera.getWidth() * BombermanGame.ZOOM));
        }

        animate ++;
        keyListen();
        if (status == 0) {
            for (int i = BombermanGame.enemies.size() - 1; i >= 0; i--) {
                if (this.intersects(BombermanGame.enemies.get(i))){
                    this.updateStatus();
                }
            }
        }
        if (status == 1) {
            if (animate == 1) {
                MyAudioPlayer deadAudio = new MyAudioPlayer(MyAudioPlayer.DEAD);
                deadAudio.play();
            }
            setImg(Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animate, 150).getFxImage());
            if (animate == 150) {
                this.appear = false;
            }
        }
    }
}
