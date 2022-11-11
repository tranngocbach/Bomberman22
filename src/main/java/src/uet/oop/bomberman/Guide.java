package src.uet.oop.bomberman;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import src.uet.oop.bomberman.screen.IntroGame;

import java.net.URISyntaxException;


public class Guide {
    private GraphicsContext render;
    private Image logo;
    private double width;
    private double height;
    private AnimationTimer loop;
    private Scene scene;
    private  final double more = 100;

    public Guide() {
        try {
            logo = new Image(getClass().getResource("/guide2.png").toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        width = 416 * 1.2 + 100;
        height = 416;
        Group group = new Group();
        Canvas canvas = new Canvas(width, height);
        group.getChildren().add(canvas);
        scene = new Scene(group, width, height);
        LoadGame.mainStage.setScene(scene);
        LoadGame.mainStage.centerOnScreen();
        render = canvas.getGraphicsContext2D();
        addLoop();
        Scale scale = new Scale();
        scale.setPivotY(0);
        scale.setPivotX(0);
        scale.setX(1.5);
        scale.setY(1.5);
        canvas.getTransforms().clear();
        canvas.getTransforms().add(scale);
        LoadGame.mainStage.setWidth(width * 1.5);
        LoadGame.mainStage.setHeight(height * 1.5 + 20);
        LoadGame.mainStage.centerOnScreen();
        LoadGame.mainStage.setResizable(false);
        LoadGame.mainStage.show();
    }

    void addLoop() {
        loop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render.clearRect(0, 0, width, height);
                draw();
                scene.setOnKeyPressed(keyEvent -> {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        loop.stop();
                        Menu m = new Menu();
                    }
                });
            }
        };
        loop.start();
    }

    void draw()
    {
        render.setFill(Color.web("BLACK"));
        render.fillRect(0, 0, 416 * 1.2 + more, 416);
        render.drawImage(logo, 30, 50, 1026 * 0.5, 455 * 0.5);
    }
}
