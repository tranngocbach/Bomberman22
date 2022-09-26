module com.example.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens src.uet.oop.bomberman to javafx.fxml;
    exports src.uet.oop.bomberman;
}