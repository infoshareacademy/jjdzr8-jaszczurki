package com.isa.control;

import com.isa.entity.Offer;
import com.isa.entity.OfferArrayFromFile;
import com.isa.entity.enums.ServiceCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.isa.entity.appConstants.AppConstants.ACCEPT_OR_BACK_TO_MENU_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.CHOOSE_A_NUMBER_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.ENTERED_WRONG_CATEGORY_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.ENTERED_WRONG_NUMBER_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.NO_OFFERS_MORE;

public class SearchOptions extends SubMenuNavigator {
    private final Scanner scanner;
    private static final String SEARCH = "Wyszukaj po lokalizacji i kategorii, możesz również podać 1 słowo kluczowe, które powinno znajdować się w treści oferty.";
    private static final String OPTIONAL_LOCALIZATION_MESSAGE = "Podaj lokalizację -> wpisz nazwę miasta (opcjonalnie): ";
    private static final String OPTIONAL_KEYWORD_MESSAGE = "Podaj słowo kluczowe (opcjonalnie): ";
    private static final String CATEGORY_FOR_SEARCH_SELECTION_MESSAGE = "Musisz podać przynajmniej 1 kategorię z dostępnych poniżej i wcisnąć \"Enter\". \nNastępnie wpisz słowo \"koniec\" żeby zakończyć wybór kategorii. Możesz podać dowolną ilość kategorii: ";
    private static final String ALREADY_CHOSEN_CATEGORY_MESSAGE = "Wybrano juź tę kategorię. Proszę wybrać inną.";
    private static final String SEARCH_CONTINUE_OR_QUIT_MESSAGE = "Czy chcesz kontynuować wyszukiwanie? (tak lub \"Enter\"/nie)";

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
            List<ServiceCategory> selectedCategories = selectServiceCategories();

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

    private List<ServiceCategory> selectServiceCategories() {
        List<ServiceCategory> selectedCategories = new ArrayList<>();
        displayServiceCategories();
        while (true) {
            String serviceCategoryNumber = scanner.nextLine();
            if (serviceCategoryNumber.equals("koniec")) {
                if (!selectedCategories.isEmpty()) {
                    break;
                }
            } else {
                ServiceCategory category = convertInputToCategory(serviceCategoryNumber);
                if (category != null) {
                    if (isCategoryAlreadySelected(selectedCategories, category)) {
                        System.out.println(ALREADY_CHOSEN_CATEGORY_MESSAGE);
                    } else {
                        selectedCategories.add(category);
                    }
                } else {
                    System.out.println(ENTERED_WRONG_CATEGORY_MESSAGE);
                }
            }
        }
        return selectedCategories;
    }

    private void displayServiceCategories() {
        System.out.println(CATEGORY_FOR_SEARCH_SELECTION_MESSAGE);
        for (ServiceCategory serviceCategory : ServiceCategory.values()) {
            System.out.println(serviceCategory);
        }
    }

    private boolean isCategoryAlreadySelected(List<ServiceCategory> selectedCategories, ServiceCategory category) {
        return selectedCategories.contains(category);
    }

    private ServiceCategory convertInputToCategory(String serviceCategoryNumber) {
        try {
            return ServiceCategory.getFromString(serviceCategoryNumber);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Offer getOfferByNumber(long numberOfferToEdit) {
        Offer offer = null;
        for (Offer offerNumber : OfferArrayFromFile.getOffersArray()) {
            if (offerNumber.getOfferID() == numberOfferToEdit) {
                offer = offerNumber;
                break;
            }
        }
        return offer;
    }
}
