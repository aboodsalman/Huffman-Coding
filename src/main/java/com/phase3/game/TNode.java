package com.phase3.game;

public class TNode implements Comparable<Object>{
    private TNode left, right;
    private int value;
    private char character;

    public TNode(int value, char character, TNode left, TNode right) {
        this.value = value;
        this.character = character;
        this.left = left;
        this.right = right;
    }
    public TNode(int value, TNode left, TNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }
    public TNode(char character) {
        this.character = character;
    }
    public TNode(){}

    public int getValue() {
        return value;
    }
    public char getCharacter() {
        return character;
    }
    public void setCharacter(char character) {
        this.character = character;
    }
    public TNode getLeft() {
        return left;
    }
    public void setLeft(TNode left) {
        this.left = left;
    }
    public TNode getRight() {
        return right;
    }
    public void setRight(TNode right) {
        this.right = right;
    }
    @Override
    public int compareTo(Object o) {
        return Integer.compare(this.value, ((TNode)o).getValue());
    }
    public boolean equals(Object o) {
        return this.value == ((TNode)o).value;
    }
}
