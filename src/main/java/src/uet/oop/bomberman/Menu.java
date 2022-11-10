package src.uet.oop.bomberman;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.net.URISyntaxException;

public class Menu  {
    public Menu()
    {
        try {
            //LoadGame.mainStage.close();
            //LoadGame.mainStage = new Stage();
            FXMLLoader loader = new FXMLLoader(new File("src\\main\\resources\\menu\\menu.fxml").toURI().toURL());
            Parent root = loader.load();
            LoadGame.mainStage.setTitle("Bomberman");
            LoadGame.mainStage.setScene(new Scene(root, 714, 410));
            LoadGame.mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


