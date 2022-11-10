package src.uet.oop.bomberman;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import src.uet.oop.bomberman.screen.IntroGame;


public class LoadGame extends Application {
    //region variable
    private GraphicsContext render;
    private double width;
    private double height;
    private IntroGame introGame;
    private AnimationTimer loop;
    static Stage mainStage;
    private Menu menu;
    private boolean initdone;
    //endregion
    //region 🡇
    //endregion
    //region function
    @Override
    public void start(Stage stage) {
        width = 416 * 1.2 + 100;
        height = 416;
        Group group = new Group();
        Canvas canvas = new Canvas(width, height);
        group.getChildren().add(canvas);
        Scene scene = new Scene(group, width, height);
        stage.setScene(scene);
        stage.centerOnScreen();
        render = canvas.getGraphicsContext2D();
        introGame = new IntroGame();
        addLoop();
        mainStage = stage;
        stage.setTitle("Loading");
        Scale scale = new Scale();
        scale.setPivotY(0);
        scale.setPivotX(0);
        scale.setX(1.5);
        scale.setY(1.5);
        canvas.getTransforms().clear();
        canvas.getTransforms().add(scale);
        stage.setWidth(width * 1.5);
        stage.setHeight(height * 1.5 + 20);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
        initdone = true;
        menu = null;
    }

    void addLoop() {
        loop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                render.clearRect(0, 0, width, height);
                introGame.draw(render);
//                introGame.setDone(true);
                if (introGame.isDone() && initdone) {
                    loop.stop();
                    if (menu == null) {
                        menu = new Menu();
                    }
                }
            }
        };
        loop.start();
    }
    //endregion
    //region 🡇
    //endregion
    //region main
    public static void main(String[] args) {
        launch(args);
    }
    //endregion
}
