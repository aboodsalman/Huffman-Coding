package com.phase3.game;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HeaderFx {
    private static Label extLengthLbl, extLbl, headerLengthLbl, treeLbl, bitsLbl;
    private static Label extLengthValLbl, extValLbl, headerLengthValLbl, treeValLbl, bitsValLbl;
    private static GridPane gp;
    private static Stage stg;

    public HeaderFx(int extLength, String extVal, int headerLength, String tree, byte bits) {
        extLengthLbl = new Label("Extension Length");
        lblStyle(extLengthLbl);
        extLengthValLbl = new Label(extLength+"");
        lblStyle(extLengthValLbl);

        extLbl = new Label("File Extension");
        lblStyle(extLbl);
        extValLbl = new Label(extVal);
        lblStyle(extValLbl);

        headerLengthLbl = new Label("Serialized Tree Length");
        lblStyle(headerLengthLbl);
        headerLengthValLbl = new Label(headerLength+"");
        lblStyle(headerLengthValLbl);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < headerLength; i += 20) {
            int end = Math.min(i + 20, headerLength);
            result.append(tree.substring(i, end)).append("\n");
        }
        tree = result.toString();
        treeLbl = new Label("Serialized Tree");
        lblStyle(treeLbl);
        treeValLbl = new Label(tree);
        lblStyle(treeValLbl);

        bitsLbl = new Label("Bits Of Last Byte");
        lblStyle(bitsLbl);
        bitsValLbl = new Label(bits+"");
        lblStyle(bitsValLbl);

        FlowPane flowPane = new FlowPane();
        flowPane.setPrefWidth(250);
        flowPane.setPrefHeight(100);

        flowPane.getChildren().add(treeValLbl);
        flowPane.setStyle("-fx-background-color: #151515");

        ScrollPane scrollPane = new ScrollPane(flowPane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        gp = new GridPane();
        gp.setStyle("-fx-background-color: #151515;");
        gp.setPadding(new Insets(20));
        gp.setVgap(10);
        gp.setHgap(10);
        gp.add(extLengthLbl, 0, 0);
        gp.add(extLengthValLbl, 1, 0);
        gp.add(extLbl, 0, 1);
        gp.add(extValLbl, 1, 1);
        gp.add(headerLengthLbl, 0, 2);
        gp.add(headerLengthValLbl, 1, 2);
        gp.add(treeLbl, 0, 3);
        gp.add(scrollPane, 1, 3);
        gp.add(bitsLbl, 0, 4);
        gp.add(bitsValLbl, 1, 4);

        stg = new Stage();
        stg.setScene(new Scene(gp));
        stg.setTitle("Header");
        stg.setResizable(false);
    }

    private void lblStyle(Label lbl) {
        lbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #CCCCCC; -fx-font-size: 20px;");
    }
    public static Stage getStage() {
        return stg;
    }
}
