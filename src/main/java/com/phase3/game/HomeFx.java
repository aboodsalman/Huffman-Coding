package com.phase3.game;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;

public class HomeFx {
    private Label lbl;
    private VBox vb;
    private Button compressBtn, decompressBtn, headerBtn, browseBtn, statBtn, huffBtn, backBtn;

    public HomeFx() {
        vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);

        lbl = new Label("Choose a file to compress / decompress using Huffman Coding");
        lbl.setStyle("-fx-text-fill: #CCCCCC; -fx-font-size: 26");

        compressBtn = new Button("Compress");
        decompressBtn = new Button("Decompress");
        browseBtn = new Button("Browse");
        headerBtn = new Button("Header");
        statBtn = new Button("Statistics");
        huffBtn = new Button("Huffman");
        backBtn = new Button("<- Back");
        btnStyle(compressBtn);
        btnStyle(decompressBtn);
        btnStyle(headerBtn);
        btnStyle(statBtn);
        btnStyle(huffBtn);
        btnStyle(browseBtn);
        btnStyle(backBtn);
        headerBtn.setDisable(true);
        statBtn.setDisable(true);
        huffBtn.setDisable(true);
        vb.getChildren().addAll(lbl, compressBtn, decompressBtn);

        compressBtn.setOnAction(e->{
            vb.getChildren().clear();
            vb.getChildren().addAll(browseBtn, headerBtn, statBtn, huffBtn, backBtn);
            headerBtn.setDisable(true);
            statBtn.setDisable(true);
            huffBtn.setDisable(true);
        });

        backBtn.setOnAction(e->{
            vb.getChildren().clear();
            vb.getChildren().addAll(lbl, compressBtn, decompressBtn);
        });
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        browseBtn.setOnAction(e -> {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                if (selectedFile.getName().endsWith(".huff")) {
                    // Show an alert if a .huff file is selected
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid File");
                    alert.setHeaderText(null);
                    alert.setContentText("The .huff file format is not allowed. Please select a different file.");
                    alert.showAndWait();
                }
                else {
                    HuffmanCode.compress(selectedFile);
                    headerBtn.setDisable(false);
                    statBtn.setDisable(false);
                    huffBtn.setDisable(false);
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select a file");
            }
        });

        headerBtn.setOnAction(e->{
            HeaderFx.getStage().show();
        });

        statBtn.setOnAction(e->{
            StatFx.getStage().show();
        });

        huffBtn.setOnAction(e->{
            HuffmanFx.getStage().show();
        });

        FileChooser fileChooser1 = new FileChooser();
        decompressBtn.setOnAction(e -> {
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Huffman Files (*.huff)", "*.huff");
            fileChooser1.getExtensionFilters().add(extFilter);
            File selectedFile = fileChooser1.showOpenDialog(stage);
            if (selectedFile != null) {
                HuffmanCode.decompress(selectedFile);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Huffman Decompression");
                alert.setHeaderText("Decompressed File");
                alert.setContentText("The file is decompressed successfully!");
                alert.showAndWait();
            } else {
                System.out.println("No file selected.");
            }
        });

    }

    private void btnStyle(Button btn){
        btn.setStyle("-fx-background-color: #151515; -fx-border-color: red; -fx-background-radius: 5;" +
                "-fx-border-radius: 5; -fx-text-fill: #CCCCCC; -fx-font-size: 22");
        btn.setMinWidth(200);
    }

    public VBox getVb() {
        return vb;
    }
}
