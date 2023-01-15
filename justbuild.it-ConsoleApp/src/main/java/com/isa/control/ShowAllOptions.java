package com.isa.control;

import com.isa.entity.Offer;
import com.isa.entity.OfferArrayFromFile;
import com.isa.entity.enums.ServiceCategory;
import java.util.InputMismatchException;
import java.util.Scanner;
import static com.isa.entity.appConstants.AppConstants.*;


public class ShowAllOptions extends SubMenuNavigator {
    private static final String SHOW_ALL = "Pokaż wszystkie ogłoszenia.";

    public void showAllDetails(){
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

        for (ServiceCategory d : ServiceCategory.values()) {
            System.out.println(d.toString());
        }
        searchByCategory();
    }

    private void searchByCategory() {
        Scanner scanner = new Scanner(System.in);
        try {
            int chose = scanner.nextInt();
            for (ServiceCategory k : ServiceCategory.values()) {
                if (chose == Integer.parseInt(k.getNumber())) {
                    System.out.println(CHOSEN_OPTION_MESSAGE + k);
                    findOfferCategory(k);
                    goBackToMenu();
                }
                if (chose < 1 || chose > ServiceCategory.values().length) {
                    System.out.println(ENTERED_WRONG_NUMBER_MESSAGE);
                    showOfferCategory();
                    break;
                }
            }
        }catch (InputMismatchException e) {
            System.out.println(ENTERED_WRONG_NUMBER_MESSAGE);
            showOfferCategory();
        }
    }

    public void findOfferCategory(ServiceCategory serviceCategory) {
        for (Offer o : OfferArrayFromFile.getOffersArray()){
            if (o.getServiceCategory().equals(serviceCategory)){
                System.out.println(o);
            }
        }
        System.out.println(NO_OFFERS_MORE);
    }
}
