package com.isa.entity.enums;

public enum MenuOptions {

    ADD(1,"DODAJ SWOJĄ OFERTĘ"),
    EDIT(2,"EDYTUJ OFERTĘ"),
    DELETE(3,"USUŃ OFERTĘ"),
    SEARCH(4,"WYSZUKAJ OFERTĘ"),
    SHOW_ALL(5,"POKAŻ WSZYSTKIE OFERTY Z WYBRANEJ KATEGORII USŁUG"),
    EXIT(6,"ZAMKNIJ PROGRAM \"justbuild.it\"");

    final int number;
    final String polishName;

    MenuOptions(int number, String polishName) {
        this.number = number;
        this.polishName = polishName;
    }

    public int getNumber() {
        return number;
    }

    public String getPolishName() {
        return polishName;
    }

    @Override
    public String toString() {
        return "MenuOptions{" +
                "number=" + number +
                ", polishName='" + polishName + '\'' +
                '}';
    }
}
