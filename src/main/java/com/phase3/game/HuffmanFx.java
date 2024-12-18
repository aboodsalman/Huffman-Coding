package com.phase3.game;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HuffmanFx {
    private static TableView<CharData> table;
    private static Stage stg;

    public HuffmanFx(int freq[], String code[]){
        table = new TableView<>();
        ObservableList<CharData> data = FXCollections.observableArrayList();

        // Populate the observable list with node data for display in the table
        for (int i=0; i<256; i++) {
            if (freq[i]>0) {
                if(i==10)
                    data.add(new CharData("Line Feed", freq[i], code[i],
                            code[i].length()*freq[i], i));
                else if(i==13)
                    data.add(new CharData("Form Feed", freq[i], code[i],
                            code[i].length()*freq[i], i));
                else if(i==32)
                    data.add(new CharData("Space", freq[i], code[i],
                            code[i].length()*freq[i], i));
                else
                    data.add(new CharData(((char)i)+"", freq[i], code[i],
                        code[i].length()*freq[i], i));
            }
        }

        // Set up table columns for character, frequency, Huffman code, code length, and ASCII value
        TableColumn<CharData, String> charColumn = new TableColumn<>("Character");
        charColumn.setCellValueFactory(new PropertyValueFactory<>("charDisplay"));
        charColumn.setPrefWidth(120);

        TableColumn<CharData, Number> freqColumn = new TableColumn<>("Frequency");
        freqColumn.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        freqColumn.setPrefWidth(120);

        TableColumn<CharData, String> codeColumn = new TableColumn<>("Huffman Code");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("huffCode"));
        codeColumn.setPrefWidth(150);

        TableColumn<CharData, Number> lengthColumn = new TableColumn<>("Code Size");
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("huffLength"));
        lengthColumn.setPrefWidth(120);

        TableColumn<CharData, Number> asciiColumn = new TableColumn<>("ASCII Value");
        asciiColumn.setCellValueFactory(new PropertyValueFactory<>("asciiValue"));
        asciiColumn.setPrefWidth(120); // Set preferred width for ASCII column

        charColumn.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold; -fx-font-size: 14px;");
        freqColumn.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold; -fx-font-size: 14px;");
        codeColumn.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold; -fx-font-size: 14px;");
        lengthColumn.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold; -fx-font-size: 14px;");
        asciiColumn.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold; -fx-font-size: 14px;"); // Style for ASCII column

        table.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(CharData item, boolean empty) {
                super.updateItem(item, empty);
                setStyle(item == null || empty ? "" : "-fx-font-weight: bold; -fx-font-size: 14px;");
            }
        });

        table.getColumns().add(charColumn);
        table.getColumns().add(asciiColumn);
        table.getColumns().add(freqColumn);
        table.getColumns().add(codeColumn);
        table.getColumns().add(lengthColumn);
        table.setItems(data);
        table.setStyle("-fx-border-color: black; -fx-border-radius: 10; -fx-background-radius: 10;");

        stg = new Stage();
        stg.setScene(new Scene(table));
        stg.setTitle("Huffman Table");
        stg.setResizable(false);
    }

    private static TableView<CharData> getTable(){
        return table;
    }
    public static Stage getStage(){
        return stg;
    }
}
