package com.isa.control;

import com.isa.enums.MenuOptions;

import java.util.Scanner;

public class MenuNavigator {

    //wyświetlanie dostępnych menu
    public static void chooseMenu() {
        System.out.println("Program justbuild.it");
        System.out.println("*****************MENU*********************");

        for (MenuOptions c : MenuOptions.values()) {
            System.out.println(c);
        }
    }

    //wybieranie menu
    public static void chooseMenuOptions() {
        System.out.println("**************Wybierz numer: *****************");

        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();

        for (MenuOptions d : MenuOptions.values()) {
            if (number == d.getNumber()) {
                System.out.println("Wybrano " + d);
                break;
            }
        }
    }
    public static void returnToMenuOptions(){

    }
}
