package src.uet.oop.bomberman;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;


public class ControlMenu {
    @FXML
    private Button button;

    @FXML
    private Button guide;

    @FXML
    private Button exit;

    public void setButton(ActionEvent actionEvent) {

        BombermanGame game = new BombermanGame();
        game.changeSceneMenu();
    }

    public void openGuide(ActionEvent actionEvent) {
        System.out.println("OK");
    }

    public void Exit(ActionEvent actionEvent) {
        System.out.println("OK");
    }
}
