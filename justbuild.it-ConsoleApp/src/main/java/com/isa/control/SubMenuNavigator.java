package com.isa.control;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class SubMenuNavigator {

    void choose(){

        boolean inProgress = true;
        do{
            Scanner scanner = new Scanner(System.in);
            int choice = 0;
            try{
                choice = scanner.nextInt();
                if (choice == 1) {
                    accept();
                } else if (choice == 2) {
                    goBackToMenu();
                    inProgress = false;
                } else {
                    System.out.println("Wpisano błędną cyfrę.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Wpisano niewłaściwe znaki");
            }
        } while(inProgress);
    }
    void accept(){
        System.out.println("Akcja wykonana poprawnie.");

    }
    void goBackToMenu(){
        System.out.println("Wracam do menu");
        MenuNavigator.printMenu();
        MenuNavigator.chooseMenuOptions();
    }
}
