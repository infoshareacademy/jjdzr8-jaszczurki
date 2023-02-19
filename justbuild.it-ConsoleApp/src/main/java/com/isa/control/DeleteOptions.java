package com.isa.control;

import com.isa.control.filesFactory.MyObjectFileStorage;
import com.isa.control.service.Service;
import com.isa.entity.Offer;

import java.util.Scanner;

import static com.isa.entity.appConstants.AppConstants.ACCEPT_OR_BACK_TO_MENU_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.ENTERED_WRONG_NUMBER_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.ENTERED_WRONG_SIGNS_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.OFFER_INFO_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.OFFER_NOT_FOUND_MESSAGE;


public class DeleteOptions extends SubMenuNavigator {

    private static final String OFFER_DELETED_SUCCESSFULLY_MESSAGE = "Oferta została usunięta.";
    private static final String ENTER_OFFER_ID_MESSAGE = "Podaj ID oferty, którą chcesz usunąć: ";
    private static final String DELETE_CONFIRMATION_MESSAGE = "Czy na pewno chcesz usunąć ofertę? (tak/nie lub \"Enter\")";
    private static final String DELETE_CANCELED_MESSAGE = "Anulowano usunięcie oferty.";

    private final SearchOptions searchOptions;
    private final Scanner scanner;

    Service service = new Service();

    public DeleteOptions() {

        searchOptions = new SearchOptions();
        scanner = new Scanner(System.in);
    }

    public void showDeleteDetails() {
        System.out.println(ACCEPT_OR_BACK_TO_MENU_MESSAGE);
        subMenuActions();
    }

    @Override
    void subMenuActions() {

        switch (scanner.nextLine()) {
            case "1" -> deleteOffer();
            case "2" -> goBackToMenu();
            default -> {
                System.out.println(ENTERED_WRONG_NUMBER_MESSAGE);
                subMenuActions();
            }
        }
    }

    public void deleteOffer() {
        System.out.println(ENTER_OFFER_ID_MESSAGE);
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
        System.out.println(DELETE_CONFIRMATION_MESSAGE);
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("tak")) {
            service.deleteOfferFromFile(offer);
            System.out.println(OFFER_DELETED_SUCCESSFULLY_MESSAGE);
        } else {
            System.out.println(DELETE_CANCELED_MESSAGE);
        }
        goBackToMenu();
    }

}

