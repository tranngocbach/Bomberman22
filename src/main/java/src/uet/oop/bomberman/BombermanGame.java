package src.uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import src.uet.oop.bomberman.audio.MyAudioPlayer;
import src.uet.oop.bomberman.entities.Bomber;
import src.uet.oop.bomberman.entities.Bomb;
import src.uet.oop.bomberman.entities.Entity;
import src.uet.oop.bomberman.entities.Grass;
import src.uet.oop.bomberman.entities.Wall;
import src.uet.oop.bomberman.entities.*;
import src.uet.oop.bomberman.graphics.Sprite;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class BombermanGame {

    public static int WIDTH = 31;
    public static int HEIGHT = 13;

    public static int curLevel = 1;
    public static boolean paused = false;
    public static boolean muted = false;

    private GraphicsContext gc;
    private Canvas canvas;
    public static List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();

    private List<Query> listQuery = new ArrayList<>();
    public static List<Entity> enemies = new ArrayList<>();
    public static char[][] mapMatrix = new char[100][100];

    public static Entity[][] mapToId = new Entity[100][100];
    public static Bomber bomberman;

    static Scanner scanner;

    public static MyAudioPlayer musicPlayer = new MyAudioPlayer(MyAudioPlayer.BACKGROUND_MUSIC);
    ;

    public MyAudioPlayer getMusicPlayer() {
        return musicPlayer;
    }

    public void setMusicPlayer(MyAudioPlayer _musicPlayer) {
        musicPlayer = _musicPlayer;
    }


    public static Set<KeyCode> pressedKeys = new HashSet<>();

    private boolean checkEndgame = false;

    private Info info;
    private int Score;
    private int Fps;
    private int Life;
    private int Time;

    public static double ZOOM;

    public static Canvas gccanvas;



    public BombermanGame (){
        Time = 100;
        Score = 0;
        Life = 3;
        ZOOM = 1.75;
    }

    private static void addScale(GraphicsContext gc,double ZOOM) {
        Scale scale = new Scale();
        scale.setPivotY(0);
        scale.setPivotX(0);
        scale.setX(ZOOM);
        scale.setY(ZOOM);
        gc.getCanvas().getTransforms().clear();
        gc.getCanvas().getTransforms().add(scale);
        Canvas canvas = gc.getCanvas();
        LoadGame.mainStage.setWidth(416 * 1.2 * ZOOM);
        LoadGame.mainStage.setHeight(416 * ZOOM + 37);
        LoadGame.mainStage.centerOnScreen();
    }

   /* public void changeScene() {        //gameover
        try {
            FXMLLoader loader = new FXMLLoader(new File("src\\main\\resources\\menu\\gameover.fxml").toURI().toURL());
            Parent root = loader.load();
            stg.setScene(new Scene(root));
            muted = true;
            enemies.clear();
            entities.clear();
            pressedKeys.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/



    static AnimationTimer timer;
    private void addLoop() {

        timer = (new AnimationTimer() {

            private LocalTime startGameTime = LocalTime.now();
            private LocalTime start = LocalTime.now();
            private LocalTime stop = LocalTime.now();
            private int t = 0;
            @Override
            public void handle(long l) {
                t++;
                stop = LocalTime.now();
                if (Duration.between(start, stop).toMillis() > 1000)
                {
                    start = stop;
                    Fps = t;
                    t = 0;
                    Time = Integer.max(0,Time - 1);
                    //System.out.println((int)Duration.between(startGameTime,stop).getSeconds());
                }
                if (paused) {
                    //halted
                } else {
                    update();
                    render();
                }
                if (muted) {
                    musicPlayer.stop();
                } else {
                    musicPlayer.loop();
                }
            }
        });

        timer.start();
    }
    public void changeSceneMenu() {         //map game

        // Tao Canvas
        checkEndgame = false;

        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);


        LoadGame.mainStage.setScene(scene);
        LoadGame.mainStage.show();

        musicPlayer.play();
        scene.setOnKeyReleased(keyEvent -> {
            pressedKeys.remove(keyEvent.getCode());
        });
        scene.setOnKeyPressed(keyEvent -> {
            pressedKeys.add(keyEvent.getCode());
            switch (keyEvent.getCode()) {
                case K: {
                    if (paused) {
                        paused = false;
                    } else {
                        paused = true;
                    }
                    break;
                }
                case M: {
                    if (muted) {
                        muted = false;
                    } else {
                        muted = true;
                    }
                    break;
                }
            }
        });


        load();

        addLoop();
    }

    public static void createMap() {
        createMapFromFile();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                mapToId[j][i] = null;
                Entity grass = new Grass(i, j, Sprite.grass.getFxImage());
                entities.add(grass);

                switch (mapMatrix[j][i]) {
                    case '#': {
                        Entity object = new Wall(i, j, Sprite.wall.getFxImage());
                        entities.add(object);
                        mapToId[j][i] = object;
                        break;
                    }
                    case '*': {
                        Entity object = new Brick(i, j, Sprite.brick.getFxImage());
                        entities.add(object);
                        mapToId[j][i] = object;
                        break;
                    }
                    case 'f': {
                        Entity powerupFlame = new PowerupFlame(i, j, Sprite.powerup_flames.getFxImage());
                        entities.add(powerupFlame);
                        Entity object = new Brick(i, j, Sprite.brick.getFxImage());
                        entities.add(object);
                        mapToId[j][i] = object;
                        break;
                    }
                    case 's': {
                        Entity speedItem = new SpeedItem(i, j, Sprite.powerup_speed.getFxImage());
                        entities.add(speedItem);
                        Entity object = new Brick(i, j, Sprite.brick.getFxImage());
                        entities.add(object);
                        mapToId[j][i] = object;
                        break;
                    }
                    case 'b': {
                        Entity bombItem = new BombItem(i, j, Sprite.powerup_bombs.getFxImage());
                        entities.add(bombItem);
                        Entity object = new Brick(i, j, Sprite.brick.getFxImage());
                        entities.add(object);
                        mapToId[j][i] = object;
                        break;
                    }
                    case 'p': {
                        mapMatrix[j][i] = ' ';
                        break;
                    }
                    case 'x': {
                        Entity object = new Portal(i, j, Sprite.portal.getFxImage());
                        entities.add(object);
                        break;
                    }
                    case '1': {
                        Entity object = new Balloom(i, j, Sprite.balloom_left1.getFxImage());
                        enemies.add(object);
                        break;
                    }
                    case '2': {
                        Entity object = new Oneal(i, j, Sprite.oneal_left1.getFxImage());
                        enemies.add(object);
                        break;
                    }
                    case '3': {
                        Entity object = new kondoria(i, j, Sprite.kondoria_left1.getFxImage());
                        enemies.add(object);
                        break;
                    }
                    case '4': {
                        Entity object = new Doll(i, j, Sprite.doll_left1.getFxImage());
                        enemies.add(object);
                        break;
                    }
                    default: {
                        Entity object = new Grass(i, j, Sprite.grass.getFxImage());
                        entities.add(object);
                        break;
                    }
                }
            }
        }
    }

    private static void createMapFromFile() {

        String filePath = "src\\main\\resources\\levels\\Level" + Integer.toString(curLevel) + ".txt";
        try {
            File file = new File(filePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            int numberLevel = 0;
            int numberRow = 0;
            int numberColumn = 0;
            if ((line = bufferedReader.readLine()) != null) {
                String[] token = line.split("\\s");
                numberLevel = Integer.parseInt(token[0]);
                numberRow = Integer.parseInt(token[1]);
                numberColumn = Integer.parseInt(token[2]);
            }
            if (numberLevel < 1 || (numberRow < 1 && numberColumn < 1)) return;

            mapMatrix = new char[100][100];
            int countRow = -1;
            while ((line = bufferedReader.readLine()) != null) {
                countRow = countRow + 1;
                for (int i = 0; i < line.length(); i++) {
                    mapMatrix[countRow][i] = line.charAt(i);
                }
            }
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        /*for (int j = 0; j < HEIGHT; j++) {
            for (int i = 0; i < WIDTH; i++){
                if(mapToId[j][i] instanceof Bomber){
                    System.out.print(i);
                    System.out.print(j);
                    System.out.print('\n');
                }
            }
            //System.out.print('\n');
        }*/

        gccanvas = gc.getCanvas();


        for (int i = 0; i < enemies.size(); i++) {
            if (!enemies.get(i).checkAppearance()) {
                Score += 100;
                listQuery.add(new Query("remove", enemies.get(i)));
            }
        }
        for (int i = 0; i < listQuery.size(); i++) {
            if (listQuery.get(i).getType() == "add") {
                enemies.add(listQuery.get(i).getE());
            } else {
                enemies.remove(listQuery.get(i).getE());
            }
        }
        listQuery.clear();


        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof Bomb) {
                Bomb b = (Bomb) entities.get(i);
                if (b.getStatus() == 1) {
                    listQuery.addAll(b.bombExplode(mapMatrix, mapToId));
                    b.setStatus(2);
                }
            }

            if (!entities.get(i).checkAppearance()) {
                listQuery.add(new Query("remove", entities.get(i)));
            }
        }

        for (int i = 0; i < listQuery.size(); i++) {
            if (listQuery.get(i).getType() == "add") {
                entities.add(listQuery.get(i).getE());
            } else {
                entities.remove(listQuery.get(i).getE());
            }
        }
        listQuery.clear();
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
        }

        info = new Info(Bomber.numberOfBombs,Score,Time,Life,Fps);

        if (!bomberman.checkAppearance() || Time == 0) {
            if(Life == 0)
            {
                timer.stop();
                Menu menu = new Menu();
            }
            else
            {
                Life--;
                Time = 100;
                load();
            }
            //changeScene();
            //checkEndgame = true;
            //System.exit(0);
        }
    }

    public void render() {

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());



        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(gc);
        }
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).render(gc);
        }
        info.draw(gc);
        addScale(gc,1.75);
    }

    public static void load() {
        if (curLevel == 6) System.exit(0);
        try {
            scanner = new Scanner(new FileReader("src\\main\\resources\\levels\\Level" + Integer.toString(curLevel) + ".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scanner.nextInt();
        HEIGHT = scanner.nextInt();
        WIDTH = scanner.nextInt();
        enemies.clear();
        entities.clear();
        pressedKeys.clear();


        scanner.nextLine();
        createMap();
        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
    }
}
