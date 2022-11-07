package src.uet.oop.bomberman;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
public class ControlGameOver {
    @FXML
    private Button exit;
    private Button restart;

    public void restartGame(ActionEvent actionEvent) {
        BombermanGame bombermanGame = new BombermanGame();
        BombermanGame.muted = false;
        if(BombermanGame.timer != null)BombermanGame.timer.stop();
        bombermanGame.changeSceneMenu();
    }

    public void exitGame(ActionEvent actionEvent) {
        System.exit(0);
    }
}
