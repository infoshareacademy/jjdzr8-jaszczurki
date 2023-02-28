package com.isa.entity.enums;

public enum ServiceCategory {
    CONSTRUCTION("1", "Budowa"),
    FINISHING_WORKS("2", "Remont"),
    INSTALLATION("3", "Instalacje"),
    ELECTRICITY("4", "Elektryka"),
    EARTH_WORKS("5", "Roboty ziemne"),
    GARDEN("6", "Ogród");

    final String number;
    final String polishName;

    ServiceCategory(String number, String polishName) {
        this.number = number;
        this.polishName = polishName;
    }

    public static ServiceCategory getFromString(String number) {
        for (ServiceCategory category : ServiceCategory.values()) {
            if (category.number.equals(number)) {
                return category;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return number + " - " + polishName;
    }
}

