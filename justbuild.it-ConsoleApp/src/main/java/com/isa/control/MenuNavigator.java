package com.isa.control;

import com.isa.entity.enums.MenuOptions;

import java.util.InputMismatchException;
import java.util.Scanner;

import static com.isa.entity.appConstants.AppConstants.*;

public class MenuNavigator {

        //wyświetlanie dostępnych menu
    public static void printMenu() {
        System.out.println(PROGRAM_NAME_MESSAGE);
        System.out.println(MENU_MESSAGE);

        for (MenuOptions c : MenuOptions.values()) {
            System.out.println(c);
        }
        System.out.println(CHOOSE_A_NUMBER_MESSAGE);

        System.out.println(CHOOSE_A_NUMBER_MESSAGE);
    }

    //wybieranie menu
    public static void chooseMenuOptions() {

        boolean inProgress = true;

        while (inProgress) {
            int number = getMenuNumberFromUser();

            for (MenuOptions d : MenuOptions.values()) {
                if (number == d.getNumber()) {
                    switch (d.getNumber()) {
                        case 1 -> {
                            System.out.println(CHOSEN_OPTION_MESSAGE + d);
                            AddOptions options = new AddOptions();
                            options.showAddDetails();
                        }
                        case 2 -> {
                            System.out.println(CHOSEN_OPTION_MESSAGE + d);
                            EditOptions options1 = new EditOptions();
                            options1.showEditDetails();
                        }
                        case 3 -> {
                            System.out.println(CHOSEN_OPTION_MESSAGE + d);
                            DeleteOptions options2 = new DeleteOptions();
                            options2.showDeleteDetails();
                        }
                        case 4 -> {
                            System.out.println(CHOSEN_OPTION_MESSAGE + d);
                            SearchOptions options3 = new SearchOptions();
                            options3.showSearchDetails();
                        }
                        case 5 -> {
                            System.out.println(CHOSEN_OPTION_MESSAGE + d);
                            ShowAllOptions options4 = new ShowAllOptions();
                            options4.showAllDetails();
                        }
                        case 6 -> {
                            System.out.println(CHOSEN_OPTION_MESSAGE + d + "\n" + GOODBYE_MESSAGE + d);
                            inProgress = false;
                        }
                    }
                    break;
                }
            }
        }
    }

    private static int getMenuNumberFromUser() {
        Scanner scanner = new Scanner(System.in);
        boolean enteringNumber = true;
        int number = 0;
        do {
            try {
                number = scanner.nextInt();
                if (number < 1 || number >= 7) {
                    System.out.println(ENTERED_WRONG_NUMBER_MESSAGE);
                } else {
                    enteringNumber = false;
                }
            } catch (InputMismatchException e) {
                System.out.println(ENTERED_WRONG_SIGNS_MESSAGE);
                scanner.next();
            }

        } while (enteringNumber);
        return number;
    }
}
