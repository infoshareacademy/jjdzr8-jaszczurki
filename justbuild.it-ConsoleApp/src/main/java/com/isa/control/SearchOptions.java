package com.isa.control;

import com.isa.control.service.Service;
import com.isa.entity.Offer;
import com.isa.entity.OfferArrayFromFile;
import com.isa.entity.enums.ServiceCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.isa.entity.appConstants.AppConstants.ACCEPT_OR_BACK_TO_MENU_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.CHOOSE_A_NUMBER_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.ENTERED_WRONG_NUMBER_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.NO_OFFERS_MORE;

public class SearchOptions extends SubMenuNavigator {
    private final Scanner scanner;
    private static final String SEARCH = "Wyszukaj po lokalizacji i kategorii, możesz również podać 1 słowo kluczowe, które powinno znajdować się w treści oferty.";
    private static final String OPTIONAL_LOCALIZATION_MESSAGE = "Podaj lokalizację -> wpisz nazwę miasta (opcjonalnie): ";
    private static final String OPTIONAL_KEYWORD_MESSAGE = "Podaj słowo kluczowe (opcjonalnie): ";

    private static final String SEARCH_CONTINUE_OR_QUIT_MESSAGE = "Czy chcesz kontynuować wyszukiwanie? (tak lub \"Enter\"/nie)";

    Service service = new Service();

    public SearchOptions() {
        this.scanner = new Scanner(System.in);
    }

    public void showSearchDetails() {
        System.out.println(ACCEPT_OR_BACK_TO_MENU_MESSAGE);
        subMenuActions();
    }

    @Override
    void subMenuActions() {

        switch (scanner.nextLine()) {
            case "1" -> showSearchOptions();
            case "2" -> goBackToMenu();
            default -> {
                System.out.println(ENTERED_WRONG_NUMBER_MESSAGE);
                subMenuActions();
            }
        }
    }

    private void showSearchOptions() {

        while (true) {
            System.out.println(SEARCH);
            System.out.println(OPTIONAL_LOCALIZATION_MESSAGE);
            String location = scanner.nextLine().toLowerCase();

            System.out.println(OPTIONAL_KEYWORD_MESSAGE);
            String keyword = scanner.nextLine().toLowerCase();

            System.out.println(CHOOSE_A_NUMBER_MESSAGE);
            List<ServiceCategory> selectedCategories = service.selectServiceCategories();

            List<Offer> matchingOffers = new ArrayList<>();
            for (Offer offer : OfferArrayFromFile.getOffersArray()) {
                if ((location.isEmpty() || offer.getCity().equals(location)) && (keyword.isEmpty() ||
                        offer.getOfferContent().contains(keyword)) && selectedCategories.contains(offer.getServiceCategory())) {
                    matchingOffers.add(offer);
                }
            }

            if (!matchingOffers.isEmpty()) {
                for (Offer offer : matchingOffers) {
                    System.out.println(offer);
                }
            } else {
                System.out.println(NO_OFFERS_MORE);
            }

            System.out.println(SEARCH_CONTINUE_OR_QUIT_MESSAGE);
            String answer = scanner.nextLine().toLowerCase();
            if (answer.equals("nie")) {
                break;
            }
        }
        goBackToMenu();
    }
}
