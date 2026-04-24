package com.candycrush.model;

public class Candy {

    public enum Type {
        RED, BLUE, GREEN, YELLOW, PURPLE, ORANGE, EMPTY
    }

    public Type type;

    public Candy(Type t) {
        this.type = t;
    }
}