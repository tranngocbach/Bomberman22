package src.uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import src.uet.oop.bomberman.entities.Bomber;
import src.uet.oop.bomberman.entities.Bomb;
import src.uet.oop.bomberman.entities.Entity;
import src.uet.oop.bomberman.entities.Grass;
import src.uet.oop.bomberman.entities.Wall;
import src.uet.oop.bomberman.entities.*;
import src.uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static int numberOfBombs = 1;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();

    private List<Query> listQuery = new ArrayList<>();
    private List<Entity> enemies = new ArrayList<>();
    public static char[][] mapMatrix = new char[100][100];

    public static Entity[][] mapToId = new Entity[100][100];
    static Bomber bomberman;

    static Bomb bomb;


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(keyEvent -> {

            switch (keyEvent.getCode()) {
                case RIGHT: {
                    bomberman.moveRight(entities, mapToId);
                    break;
                }
                case LEFT: {
                    bomberman.moveLeft(entities, mapToId);
                    break;
                }
                case UP: {
                    bomberman.moveUp(entities, mapToId);
                    break;
                }
                case DOWN: {
                    bomberman.moveDown(entities, mapToId);
                    break;
                }
                case SPACE: {
                    if (numberOfBombs > 0) {
                        bomb = bomberman.placeBomb();
                        entities.add(bomb);
                        numberOfBombs --;
                    }
                }
            }
        });


        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        createMap();

        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
    }

    public void createMap() {
        createMapFromFile();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
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
                        break;
                    }
                    case 's': {
                        Entity speedItem = new SpeedItem(i, j, Sprite.powerup_speed.getFxImage());
                        entities.add(speedItem);
                        Entity object = new Brick(i, j, Sprite.brick.getFxImage());
                        entities.add(object);
                        break;
                    }
                    case 'b': {
                        Entity bombItem = new BombItem(i, j, Sprite.powerup_bombs.getFxImage());
                        entities.add(bombItem);
                        Entity object = new Brick(i, j, Sprite.brick.getFxImage());
                        entities.add(object);
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
//                    case '1': {
//                        Entity object = new Balloom(i, j, Sprite.balloom_left1.getFxImage());
//                        enemies.add(object);
//                        break;
//                    }
                    case '2': {
                        Entity object = new Oneal(i, j, Sprite.oneal_left1.getFxImage());
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

    private void createMapFromFile() {
        String filePath = "C:\\Users\\Admin\\Downloads\\Bomberman22\\src\\main\\resources\\levels\\Level1.txt";
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
        if (!bomberman.checkAppearance()) {
            System.exit(0);
        }


        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i) instanceof Oneal) {
                ((Oneal) enemies.get(i)).getInfo(bomberman.getX(), bomberman.getY(), entities);
            }
            if (!enemies.get(i).checkAppearance()) {
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

        listQuery.addAll(bomberman.powerUp(mapMatrix, mapToId));
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof Bomb) {
                Bomb b = (Bomb) entities.get(i);
                if (b.getStatus() == 1) {
                    listQuery.addAll(b.bombExplode(mapMatrix, mapToId));
                    b.setStatus(2);
                }
            }

            if (entities.get(i) instanceof Portal) {
                entities.get(i).updateStatus();
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
        entities.forEach(e -> e.update(mapToId));
        enemies.forEach(e -> e.update(mapToId));
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        enemies.forEach(g -> g.render(gc));
    }

}
