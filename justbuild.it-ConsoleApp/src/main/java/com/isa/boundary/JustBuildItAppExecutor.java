package com.isa.boundary;

import com.isa.control.MenuNavigator;
import com.isa.entity.OfferArrayFromFile;

public class JustBuildItAppExecutor {
    public static void main(String[] args) {
        MenuNavigator.printMenu();
        OfferArrayFromFile.setOffersArray();
        MenuNavigator.chooseMenuOptions();
    }
}

