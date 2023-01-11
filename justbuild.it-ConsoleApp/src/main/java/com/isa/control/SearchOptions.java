package com.isa.control;

import com.isa.entity.Offer;
import com.isa.entity.OfferArrayFromFile;
import com.isa.entity.enums.ServiceCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.isa.entity.appConstants.AppConstants.*;

public class SearchOptions extends SubMenuNavigator{
    private final Scanner scanner;
    private static final String SEARCH = "Wyszukaj po lokalizacji i kategorii, możesz również podać 1 słowo kluczowe, które powinno znajdować się w treści oferty.";
    private static final String OPTIONAL_LOCALIZATION_MESSAGE = "Podaj lokalizację (opcjonalnie): ";
    private static final String OPTIONAL_KEYWORD_MESSAGE = "Podaj słowo kluczowe (opcjonalnie): ";
    private static final String CATEGORY_FOR_SEARCH_SELECTION_MESSAGE = "Musisz podać przynajmniej 1 kategorię z dostępnych -> Budowa, Remont, Instalacje, Elektryka, Roboty ziemne, Ogród i wcisnąć \"Enter\". \nNastępnie wpisz słowo \"koniec\" żeby zakończyć wybór kategorii. Możesz podać dowolną ilość kategorii: ";
    private static final String ALREADY_CHOSEN_CATEGORY_MESSAGE = "Wybrano juź tę kategorię. Proszę wybrać inną.";
    private static final String SEARCH_CONTINUE_OR_QUIT_MESSAGE = "Czy chcesz kontynuować wyszukiwanie? (tak/nie)";

    public SearchOptions() {
        this.scanner = new Scanner(System.in);
    }

    public void showSearchDetails(){
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

    private void showSearchOptions(){

        while (true) {
            System.out.println(SEARCH);
            System.out.println(OPTIONAL_LOCALIZATION_MESSAGE);
            String location = scanner.nextLine().toLowerCase();
            if (location.isEmpty()) {
                location = "";
            }

            System.out.println(OPTIONAL_KEYWORD_MESSAGE);
            String keyword = scanner.nextLine().toLowerCase();
            if (keyword.isEmpty()) {
                keyword = "";
            }

            System.out.println(CHOOSE_A_NUMBER_MESSAGE);
            System.out.println(CATEGORY_FOR_SEARCH_SELECTION_MESSAGE);
            for (ServiceCategory d : ServiceCategory.values()){
                System.out.println(d.toString());
            }

            List<ServiceCategory> selectedCategories = new ArrayList<>();

            while (true) {
                String serviceCategory = scanner.nextLine();
                while (!serviceCategory.equals("koniec")) {
                    ServiceCategory category = getServiceCategory(serviceCategory);
                    if (category != null) {
                        if (selectedCategories.contains(category)) {
                            System.out.println(ALREADY_CHOSEN_CATEGORY_MESSAGE);
                        } else {
                            selectedCategories.add(category);
                        }
                    } else {
                        System.out.println(ENTERED_WRONG_CATEGORY_MESSAGE);
                    }
                    serviceCategory = scanner.nextLine();
                }
                if (!selectedCategories.isEmpty()) {
                    break;
                }
            }

            List<Offer> matchingOffers = new ArrayList<>();
            for (Offer o : OfferArrayFromFile.getOffersArray()) {
                if ((location.isEmpty() || o.getCity().equals(location)) && (keyword.isEmpty() ||
                        o.getOfferContent().contains(keyword)) && selectedCategories.contains(o.getServiceCategory())) {
                    matchingOffers.add(o);
                }
            }

            if (!matchingOffers.isEmpty()) {
                for (Offer o : matchingOffers) {
                    System.out.println(o);
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

    private ServiceCategory getServiceCategory(String categoryName) {
        return switch (categoryName) {
            case "1" -> ServiceCategory.CONSTRUCTION;
            case "2" -> ServiceCategory.FINISHING_WORKS;
            case "3" -> ServiceCategory.INSTALLATION;
            case "4" -> ServiceCategory.ELECTRICITY;
            case "5" -> ServiceCategory.EARTH_WORKS;
            case "6" -> ServiceCategory.GARDEN;
            default -> null;
        };
    }

}
