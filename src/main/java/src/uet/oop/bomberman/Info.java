package src.uet.oop.bomberman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import src.uet.oop.bomberman.graphics.Sprite;

import java.net.URISyntaxException;
import java.util.LinkedList;



public class Info{
    private final LinkedList<Image> info;
    private String BOMBCONTROLLED;
    private String SCORE;
    private String Time;

    private String LIFE;

    private String FPS;

    public Info(int BOMBCONTROLLED,int SCORE,int Time,int LIFE,int FPS) {
        this.BOMBCONTROLLED = Integer.toString(BOMBCONTROLLED);
        this.SCORE = Integer.toString(SCORE);
        this.Time = Integer.toString(Time);
        this.LIFE = Integer.toString(LIFE);
        this.FPS = Integer.toString(FPS);
        info = new LinkedList<>();

        info.add(Sprite.player_down.getFxImage());
        info.add(Sprite.bomb.getFxImage());
    }


    public void draw(GraphicsContext render) {
        double x = render.getCanvas().getLayoutX() / BombermanGame.ZOOM;
        double row = 20;
        render.setFill(Color.BLACK);
        render.drawImage(info.get(0), -x + 30, row - 20,30,30);
        render.drawImage(info.get(1), -x + 95, row - 20,30,30);

        render.fillText(" x", -x + 52, row);
        render.fillText(" x", -x + 125, row);

        String text = LIFE + "";
        render.fillText(text, -x + 65, row);
        text = BOMBCONTROLLED + "";
        render.fillText(text, -x + 138, row);
        Text calc = new Text("Điểm số: "+  SCORE + "");

        render.fillText(calc.getText(),
                -x + 245.5 - calc.getBoundsInLocal().getWidth() / 2, row);
        text = "Thời gian: " + Time + "";
        render.fillText(text, -x + 350, row);
        text = "FPS: " + FPS;
        render.fillText(text, -x + 430, row);
    }
}
