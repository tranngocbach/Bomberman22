package src.uet.oop.bomberman;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;


public class ControlMenu {
    @FXML
    private Button button;


    public void setButton(ActionEvent actionEvent) {
        BombermanGame bombermanGame = new BombermanGame();
        bombermanGame.changeScene();
    }
}
