package com.isa.control;

import com.isa.control.service.Service;
import com.isa.entity.enums.ServiceCategory;

import java.util.Scanner;

import static com.isa.entity.appConstants.AppConstants.ACCEPT_OR_BACK_TO_MENU_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.CHOOSE_A_NUMBER_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.CHOSEN_OPTION_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.ENTERED_WRONG_NUMBER_MESSAGE;

public class ShowAllOptions extends SubMenuNavigator {
    private static final String SHOW_ALL = "Pokaż wszystkie ogłoszenia.";

    Service service = new Service();

    public void showAllDetails() {
        System.out.println(SHOW_ALL);
        System.out.println(ACCEPT_OR_BACK_TO_MENU_MESSAGE);
        subMenuActions();
    }

    @Override
    void subMenuActions() {
        Scanner scanner = new Scanner(System.in);

        switch (scanner.nextLine()) {
            case "1" -> showOfferCategory();
            case "2" -> goBackToMenu();
            default -> {
                System.out.println(ENTERED_WRONG_NUMBER_MESSAGE);
                subMenuActions();
            }
        }
    }

    private void showOfferCategory() {
        System.out.println(CHOOSE_A_NUMBER_MESSAGE);
        for (ServiceCategory serviceCategory : ServiceCategory.values()) {
            System.out.println(serviceCategory);
        }
        searchByCategory();
    }

    private void searchByCategory() {
        Scanner scanner = new Scanner(System.in);
        try {
            String userChoose = scanner.nextLine();
            ServiceCategory searchCategory = ServiceCategory.getFromString(userChoose);
            System.out.println(CHOSEN_OPTION_MESSAGE + searchCategory);
            service.findOfferCategory(searchCategory);
            goBackToMenu();
        } catch (IllegalArgumentException e) {
            System.out.println(ENTERED_WRONG_NUMBER_MESSAGE);
            showOfferCategory();
        }
    }
}
