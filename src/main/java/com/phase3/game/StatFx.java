package com.phase3.game;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StatFx {
    private static Label beforeLbl, afterLbl, percentLbl;
    private static VBox vb;
    private static Stage stg;

    public StatFx(int beforeBits, int afterBits) {
        beforeLbl = new Label("Size Before Compressing: " + beforeBits+" bits");
        afterLbl = new Label("Size After Compressing: " + afterBits+" bits");
        percentLbl = new Label("Percentage Compressing: ");
        lblStyle(beforeLbl);
        lblStyle(afterLbl);
        lblStyle(percentLbl);

        HBox hb = new HBox(10);
        hb.getChildren().addAll(percentLbl, getPercentagePane(beforeBits, afterBits));

        vb = new VBox(20);
        vb.setPadding(new Insets(20));
        vb.getChildren().addAll(beforeLbl, afterLbl, hb);
        vb.setStyle("-fx-background-color: #333333;");

        stg = new Stage();
        stg.setScene(new Scene(vb));
        stg.setTitle("Statistics");
        stg.setResizable(false);
    }

    private void lblStyle(Label lbl) {
        lbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #CCCCCC; -fx-font-size: 20px;");
    }
    public static Stage getStage() {
        return stg;
    }
    private Pane getPercentagePane(int lengthBefore, int lengthAfter) {
        double percentage = ((double) lengthAfter / lengthBefore);
        String percentageText = String.format("%.4f%%", percentage * 100);;
        if (percentage > 1) {
            percentage = 1;
            percentageText = "More than " + String.format("%.4f%%", percentage * 100);
        }

        Text percentageDisplay = new Text(percentageText);
        percentageDisplay.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
        percentageDisplay.setFill(Color.BLACK);

        double paneWidth = 200;
        double paneHeight = 50;

        Rectangle outerRectangle = new Rectangle(0, 0, paneWidth, paneHeight);
        outerRectangle.setFill(Color.LIGHTGRAY);
        outerRectangle.setArcWidth(20);
        outerRectangle.setArcHeight(20);
        outerRectangle.setStroke(Color.web("#9398f5"));
        outerRectangle.setStrokeWidth(3);

        Rectangle filledRectangle = new Rectangle(0, 0, paneWidth * percentage, paneHeight);
        if (percentage >= 1)
            filledRectangle.setFill(Color.web("#f08688"));
        else
            filledRectangle.setFill(Color.web("#9398f5"));
        filledRectangle.setArcWidth(20);
        filledRectangle.setArcHeight(20);
        filledRectangle.setClip(new Rectangle(0, 0, paneWidth * percentage, paneHeight));

        percentageDisplay.setLayoutX((paneWidth - percentageDisplay.getBoundsInLocal().getWidth()) / 2);
        percentageDisplay.setLayoutY((paneHeight + percentageDisplay.getBoundsInLocal().getHeight()) / 2);

        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #333333;");
        pane.setPrefSize(paneWidth, paneHeight);
        pane.getChildren().addAll(outerRectangle, filledRectangle, percentageDisplay);
        pane.setMinWidth(Region.USE_PREF_SIZE);
        pane.setMaxWidth(Region.USE_PREF_SIZE);

        return pane;
    }
}
