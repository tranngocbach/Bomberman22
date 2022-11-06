package src.uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import src.uet.oop.bomberman.BombermanGame;
import src.uet.oop.bomberman.audio.MyAudioPlayer;

public class PowerupFlame extends Entity{
    public PowerupFlame(int x, int y, Image img) {
        super(x, y, img);
        this.passable = true;
    }

    @Override
    public void update() {
        if (this.intersects(BombermanGame.bomberman)){
            MyAudioPlayer placeSound = new MyAudioPlayer(MyAudioPlayer.POWER_UP);
            placeSound.play();
            BombermanGame.bomberman.explodeDistance ++;
            this.destroy();
        }
    }
}
