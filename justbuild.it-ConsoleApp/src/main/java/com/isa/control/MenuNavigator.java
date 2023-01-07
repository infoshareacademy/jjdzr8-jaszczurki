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
    }

    //wybieranie menu
    public static void chooseMenuOptions() {
        System.out.println(CHOOSE_A_NUMBER_MESSAGE);

        boolean inProgress = false;

        do {
            int number = getMenuNumberFromUser();

            for (MenuOptions d : MenuOptions.values()) {
                if (number == d.getNumber()) {
                    switch (d.getNumber()) {
                        case 1:
                            System.out.println(CHOSEN_OPTION_MESSAGE + d);
                            AddOptions options = new AddOptions();
                            options.showAddDetails();
                            break;
                        case 2:
                            System.out.println(CHOSEN_OPTION_MESSAGE + d);
                            EditOptions options1 = new EditOptions();
                            options1.showEditDetails();
                            break;
                        case 3:
                            System.out.println(CHOSEN_OPTION_MESSAGE + d);
                            DeleteOptions options2 = new DeleteOptions();
                            options2.showDeleteDetails();
                            break;
                        case 4:
                            System.out.println(CHOSEN_OPTION_MESSAGE + d);
                            SearchOptions options3 = new SearchOptions();
                            options3.showSearchDetails();
                            break;
                        case 5:
                            System.out.println(CHOSEN_OPTION_MESSAGE + d + "\n" + GOODBYE_MESSAGE + d);
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
