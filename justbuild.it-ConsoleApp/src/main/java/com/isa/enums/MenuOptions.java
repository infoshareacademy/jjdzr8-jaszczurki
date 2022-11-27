package com.isa.enums;

public enum MenuOptions {

    ADD(1),
    EDIT(2),
    DELETE(3),
    CLOSE(4),
    SEARCH(5);

    int number;
    MenuOptions(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number + " - " + name();
    }
}