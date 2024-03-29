package com.isa.control;

import com.isa.control.service.Service;
import com.isa.entity.Offer;
import com.isa.entity.User;
import com.isa.entity.enums.ServiceCategory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

import static com.isa.entity.appConstants.AppConstants.ACCEPT_OR_BACK_TO_MENU_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.ENTERED_WRONG_CATEGORY_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.ENTERED_WRONG_NUMBER_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.ENTERED_WRONG_SIGNS_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.OFFER_INFO_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.OFFER_NOT_FOUND_MESSAGE;

public class EditOptions extends SubMenuNavigator {
    private final Scanner scanner;
    private final SearchOptions searchOptions;
    private static final String EDIT = "Edytuj ogłoszenie.";
    private static final String OFFER_NUMBER_TO_CHANGE_MESSAGE = "Podaj numer oferty, którą chcesz zmienić: ";
    private static final String OFFER_READY_TO_EDIT_MESSAGE = "\nPodaj nowe informacje o ofercie (pozostawienie pustego pola oznacza brak zmian).";
    private static final String CATEGORY_READY_TO_EDIT_MESSAGE = "Zmień swoją kategorię usługi: \n1 - Budowa \n2 - Remont \n3 - Instalacje \n4 - Elektryka \n5 - Roboty ziemne \n6 - Ogród";
    private static final String OFFER_CONTENT_READY_TO_EDIT_MESSAGE = "Zmień treść oferty: ";
    private static final String LOCALIZATION_READY_TO_EDIT_MESSAGE = "Zmień swoją lokalizację (nazwa miasta): ";
    private static final String FIRST_NAME_READY_TO_EDIT_MESSAGE = "Zmień imię: ";
    private static final String LAST_NAME_READY_TO_EDIT_MESSAGE = "Zmień nazwisko: ";
    private static final String COMPANY_NAME_READY_TO_EDIT_MESSAGE = "Zmień nazwę firmy: ";
    private static final String EMAIL_READY_TO_EDIT_MESSAGE = "Zmień adres e-mail: ";
    private static final String PHONE_NUMBER_READY_TO_EDIT_MESSAGE = "Zmień numer telefonu: ";
    private static final String CHANGES_CONFIRMATION_MESSAGE = "Czy na pewno chcesz zapisać zmiany? (tak/nie lub \"Enter\")";
    private static final String OFFER_CHANGED_MESSAGE = "Oferta zmieniona: ";
    private static final String CHANGES_SAVED_MESSAGE = "Zmiany zapisano.";
    private static final String CHANGES_CANCELED_MESSAGE = "Anulowano zmiany.";

    Service service = new Service();

    public EditOptions() {
        this.scanner = new Scanner(System.in);
        searchOptions = new SearchOptions();
    }

    public void showEditDetails() {

        System.out.println(EDIT);
        System.out.println(ACCEPT_OR_BACK_TO_MENU_MESSAGE);
        subMenuActions();
    }

    @Override
    void subMenuActions() {

        switch (scanner.nextLine()) {
            case "1" -> showEditOptions();
            case "2" -> goBackToMenu();
            default -> {
                System.out.println(ENTERED_WRONG_NUMBER_MESSAGE);
                subMenuActions();
            }
        }
    }

    private void showEditOptions() {
        System.out.println(OFFER_NUMBER_TO_CHANGE_MESSAGE);
        int offerId = 0;
        boolean isValidOfferId = false;
        while (!isValidOfferId) {
            try {
                offerId = Integer.parseInt(scanner.nextLine().trim());
                if (offerId < 1) {
                    System.out.println(ENTERED_WRONG_NUMBER_MESSAGE);
                } else {
                    isValidOfferId = true;
                }
            } catch (NumberFormatException e) {
                System.out.println(ENTERED_WRONG_SIGNS_MESSAGE);
            }
        }

        Offer offer = searchOptions.service.getOfferByNumber(offerId);

        while (offer == null) {
            System.out.println(OFFER_NOT_FOUND_MESSAGE);
            offerId = 0;
            isValidOfferId = false;
            while (!isValidOfferId) {
                try {
                    offerId = Integer.parseInt(scanner.nextLine().trim());
                    if (offerId < 1) {
                        System.out.println(ENTERED_WRONG_NUMBER_MESSAGE);
                    } else {
                        isValidOfferId = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(ENTERED_WRONG_SIGNS_MESSAGE);
                }
            }
            offer = searchOptions.service.getOfferByNumber(offerId);
        }

        System.out.println(OFFER_INFO_MESSAGE);
        System.out.println(offer.printOffer());
        System.out.println(OFFER_READY_TO_EDIT_MESSAGE);
        scanner.nextLine();

        System.out.println(CATEGORY_READY_TO_EDIT_MESSAGE);
        String serviceCategoryNumber = scanner.nextLine();
        if (!serviceCategoryNumber.isEmpty()) {
            try {
                ServiceCategory category = ServiceCategory.getFromString(serviceCategoryNumber);
                offer.setServiceCategory(category);
            } catch (IllegalArgumentException e) {
                System.out.println(ENTERED_WRONG_CATEGORY_MESSAGE);
            }
        }

        System.out.println(OFFER_CONTENT_READY_TO_EDIT_MESSAGE);
        String offerContent = scanner.nextLine().toLowerCase();
        if (!offerContent.isEmpty()) {
            offer.setOfferContent(offerContent);
        }

        System.out.println(LOCALIZATION_READY_TO_EDIT_MESSAGE);
        String city = scanner.nextLine().toLowerCase();
        if (!city.isEmpty()) {
            offer.setCity(city);
        }

        User modificatedUser = offer.getUser();

        System.out.println(FIRST_NAME_READY_TO_EDIT_MESSAGE);
        String firstName = scanner.nextLine();
        if (!firstName.isEmpty()) modificatedUser.setFirstName(firstName);

        System.out.println(LAST_NAME_READY_TO_EDIT_MESSAGE);
        String lastName = scanner.nextLine();
        if (!lastName.isEmpty()) modificatedUser.setLastName(lastName);

        System.out.println(COMPANY_NAME_READY_TO_EDIT_MESSAGE);
        String companyName = scanner.nextLine();
        if (!companyName.isEmpty()) modificatedUser.setCompany(companyName);

        System.out.println(EMAIL_READY_TO_EDIT_MESSAGE);
        String email = scanner.nextLine();
        if (!email.isEmpty()) modificatedUser.setEmailAddress(email);

        System.out.println(PHONE_NUMBER_READY_TO_EDIT_MESSAGE);
        String phoneNumber = scanner.nextLine();
        if (!phoneNumber.isEmpty()) modificatedUser.setTelephoneNumber(phoneNumber);


        System.out.println(CHANGES_CONFIRMATION_MESSAGE);
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("tak")) {
            offer.setDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            System.out.println(OFFER_CHANGED_MESSAGE + offer.getDate());
            service.saveOfferChanges(offer);
            System.out.println(CHANGES_SAVED_MESSAGE);
        } else {
            System.out.println(CHANGES_CANCELED_MESSAGE);
        }
        goBackToMenu();
    }
}

