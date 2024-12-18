package com.phase3.game;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CharData {
    private SimpleStringProperty charDisplay;
    private SimpleIntegerProperty frequency;
    private SimpleStringProperty huffCode;
    private SimpleIntegerProperty huffLength;
    private SimpleIntegerProperty asciiValue; // New property for ASCII value

    public CharData(String charCode, int freq, String huffCode, int huffLength, int asciiValue) {
        this.charDisplay = new SimpleStringProperty(charCode);
        this.frequency = new SimpleIntegerProperty(freq);
        this.huffCode = new SimpleStringProperty(huffCode);
        this.huffLength = new SimpleIntegerProperty(huffLength);
        this.asciiValue = new SimpleIntegerProperty(asciiValue); // Initialize with byte value of charCode
    }

    public String getCharDisplay() {
        return charDisplay.get();
    }

    public int getFrequency() {
        return frequency.get();
    }

    public String getHuffCode() {
        return huffCode.get();
    }

    public int getHuffLength() {
        return huffLength.get();
    }

    public int getAsciiValue() {
        return asciiValue.get(); // Getter for the ASCII value
    }
}