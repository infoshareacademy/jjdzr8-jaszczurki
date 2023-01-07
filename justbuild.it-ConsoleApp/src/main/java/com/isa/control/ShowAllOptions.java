package com.isa.control;

import com.isa.entity.Offer;
import com.isa.entity.OfferArrayFromFile;
import com.isa.entity.enums.ServiceCategory;
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

    private void showOfferCategory(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(CHOOSE_A_NUMBER_MESSAGE);

        for (ServiceCategory d : ServiceCategory.values()){
            System.out.println(d.toString());
        }

        switch (scanner.nextLine()) {
            case "1" -> {
                System.out.println(CHOSEN_OPTION_MESSAGE + ServiceCategory.CONSTRUCTION.name());
                findOfferCategory(ServiceCategory.CONSTRUCTION);
            }
            case "2" -> {
                System.out.println(CHOSEN_OPTION_MESSAGE + ServiceCategory.FINISHING_WORKS);
                findOfferCategory(ServiceCategory.FINISHING_WORKS);
            }
            case "3" -> {
                System.out.println(CHOSEN_OPTION_MESSAGE + ServiceCategory.INSTALLATION);
                findOfferCategory(ServiceCategory.INSTALLATION);
            }
            case "4" -> {
                System.out.println(CHOSEN_OPTION_MESSAGE + ServiceCategory.ELECTRICITY);
                findOfferCategory(ServiceCategory.ELECTRICITY);
            }
            case "5" -> {
                System.out.println(CHOSEN_OPTION_MESSAGE + ServiceCategory.EARTH_WORKS);
                findOfferCategory(ServiceCategory.EARTH_WORKS);
            }
            case "6" -> {
                System.out.println(CHOSEN_OPTION_MESSAGE + ServiceCategory.GARDEN);
                findOfferCategory(ServiceCategory.GARDEN);
            }
            default -> {
                System.out.println(ENTERED_WRONG_NUMBER_MESSAGE);
                showOfferCategory();
            }
        }
        goBackToMenu();
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
