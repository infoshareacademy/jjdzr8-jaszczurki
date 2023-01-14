package com.isa.entity.enums;

public enum ServiceCategory {
    CONSTRUCTION(1),
    FINISHING_WORKS(2),
    INSTALLATION(3),
    ELECTRICITY(4),
    EARTH_WORKS(5),
    GARDEN(6);

    final int number;

    ServiceCategory(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number + " - " + name();
    }
}

