module com.phase3.game {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.phase3.game to javafx.fxml;
    exports com.phase3.game;
}