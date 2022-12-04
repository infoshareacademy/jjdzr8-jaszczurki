package com.isa.control;

import com.isa.enums.MenuOptions;

import java.util.Scanner;

import static com.isa.control.AddOptions.showAddDetails;
import static com.isa.control.DeleteOptions.showDeleteDetails;
import static com.isa.control.EditOptions.showEditDetails;
import static com.isa.control.SearchOptions.showSearchDetails;

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
        boolean inProgress = false;

        do{
            int number = scanner.nextInt();

            for (MenuOptions d : MenuOptions.values()) {
                if (number == d.getNumber()) {
                    switch (d.getNumber()){
                        case 1:
                            System.out.println("Wybrano " + d);
                            showAddDetails();
                            break;
                        case 2:
                            System.out.println("Wybrano " + d);
                            showEditDetails();
                            break;
                        case 3:
                            System.out.println("Wybrano " + d);
                            showDeleteDetails();
                            break;
                        case 4:
                            System.out.println("Wybrano " + d);
                            showSearchDetails();
                            break;
                        case 5:
                            System.out.println("Wybrano " + d + "\n" + "Dziękujemy, do widzenia!");
                            return;
                    }
                    inProgress = true;


                    break;
                }

            }
        }while(inProgress);

    }
    public static void returnToMenuOptions(){

    }
    public static void showDetails(){

    }
}
