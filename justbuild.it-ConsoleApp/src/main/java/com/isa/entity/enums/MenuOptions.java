package com.isa.entity.enums;

public enum MenuOptions {

    ADD(1),
    EDIT(2),
    DELETE(3),
    SEARCH(4),
    SHOW_ALL(5),
    EXIT(6);

    final int number;
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
