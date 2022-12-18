package com.isa.control;

import com.isa.enums.MenuOptions;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuNavigator {


    //wyświetlanie dostępnych menu
    public static void printMenu() {
        System.out.println("Program justbuild.it");
        System.out.println("*****************MENU*********************");

        for (MenuOptions c : MenuOptions.values()) {
            System.out.println(c);
        }
    }

    //wybieranie menu
    public static void chooseMenuOptions() {
        System.out.println("************** Wybierz numer: *****************");

        boolean inProgress = false;

        do {
            int number = getMenuNumberFromUser();

            for (MenuOptions d : MenuOptions.values()) {
                if (number == d.getNumber()) {
                    switch (d.getNumber()) {
                        case 1:
                            System.out.println("Wybrano " + d);
                            AddOptions options = new AddOptions();
                            options.showAddDetails();
                            break;
                        case 2:
                            System.out.println("Wybrano " + d);
                            EditOptions options1 = new EditOptions();
                            options1.showEditDetails();
                            break;
                        case 3:
                            System.out.println("Wybrano " + d);
                            DeleteOptions options2 = new DeleteOptions();
                            options2.showDeleteDetails();
                            break;
                        case 4:
                            System.out.println("Wybrano " + d);
                            SearchOptions options3 = new SearchOptions();
                            options3.showSearchDetails();
                            break;
                        case 5:
                            System.out.println("Wybrano " + d + "\n" + "Dziękujemy, do widzenia!");
                            return;
                    }
                    inProgress = true;
                    break;
                }

            }
        } while (inProgress);

    }

    private static int getMenuNumberFromUser() {
        Scanner scanner = new Scanner(System.in);
        boolean enteringNumber = true;
        int number = 0;
        do {
            try {
                number = scanner.nextInt();
                if (number < 1 || number >= 6) {
                    System.out.println("Wybrano niewłaściwą cyfrę.");
                } else {
                    enteringNumber = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Podano niewłaściwe znaki.");
                scanner.next();
            }

        } while (enteringNumber);
        return number;
    }

}
