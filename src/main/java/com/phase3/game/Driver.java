package com.phase3.game;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;

public class Driver extends Application {

    private static StackPane stackPane;
    private static BorderPane borderPane;
    private static Button exitBtn;
    @Override
    public void start(Stage stage) throws IOException {
        Locale.setDefault(Locale.ENGLISH);
        stackPane = new StackPane();
        ImageView imageView = new ImageView("C:/Users/LaptopCenter/Desktop/photos/background.jpg");

        stackPane.getChildren().add(imageView);

        HomeFx homeFx = new HomeFx();

        borderPane = new BorderPane();
        borderPane.setCenter(homeFx.getVb());

        exitBtn = new Button("<- Exit");
        exitBtn.setStyle("-fx-background-color: #151515; -fx-border-radius: 10; -fx-border-color: red; " +
                "-fx-font-size: 16px; -fx-background-radius: 10; -fx-text-fill: #CCCCCC");

        exitBtn.setOnAction(e -> System.exit(0));

        borderPane.setTop(exitBtn);
        BorderPane.setMargin(exitBtn, new Insets(150, 0, 0, 240));
        BorderPane.setMargin(homeFx.getVb(), new Insets(-200, 0, 0, 0));

        stackPane.getChildren().add(borderPane);

        Scene scene = new Scene(stackPane);

        stage.setScene(scene);
        stage.show();
    }
}