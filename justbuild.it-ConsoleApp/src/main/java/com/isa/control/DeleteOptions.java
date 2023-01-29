package com.isa.control;

import com.isa.control.filesFactory.MyObjectFileStorage;
import com.isa.entity.Offer;
import com.isa.entity.OfferArrayFromFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static com.isa.entity.appConstants.AppConstants.ACCEPT_OR_BACK_TO_MENU_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.ENTERED_WRONG_NUMBER_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.ENTERED_WRONG_SIGNS_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.FILE_READ_OR_WRITE_ERROR_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.OFFERS_FILEPATH;
import static com.isa.entity.appConstants.AppConstants.OFFER_NOT_FOUND_MESSAGE;


public class DeleteOptions extends SubMenuNavigator {

    private static final String OFFER_DELETED_SUCCESSFULLY_MESSAGE = "Ofera została usunięta.";
    private static final String ENTER_OFFER_ID_MESSAGE = "Podaj ID oferty, którą chcesz usunąć: ";


    private SearchOptions searchOptions;
    private Scanner scanner;
    private MyObjectFileStorage fileStorage;


    public DeleteOptions() {

        searchOptions = new SearchOptions();
        scanner = new Scanner(System.in);
        fileStorage = new MyObjectFileStorage();
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

        Offer offer = searchOptions.getOfferByNumber(offerId);

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
            offer = searchOptions.getOfferByNumber(offerId);
        }

        offer.printOffer();
        deleteOfferFromFile(offer);
        System.out.println(OFFER_DELETED_SUCCESSFULLY_MESSAGE);
        goBackToMenu();
    }

    public void showDeleteDetails() {
        System.out.println(ACCEPT_OR_BACK_TO_MENU_MESSAGE);
        subMenuActions();
    }

    private void deleteOfferFromFile(Offer offer) {
        try {
            List<Offer> offersList = OfferArrayFromFile.getOffersArray();
            int idToDelete = -1;
            for (int i = 0; i < offersList.size(); i++) {
                if (Objects.equals(offersList.get(i).getOfferID(), offer.getOfferID())) {
                    idToDelete = i;
                    break;
                }
            }
            if (idToDelete > -1) {
                offersList.remove(idToDelete);
                fileStorage.saveToFile(offersList, OFFERS_FILEPATH);
            } else {
                System.out.println(OFFER_NOT_FOUND_MESSAGE);
                goBackToMenu();
            }
        } catch (IOException e) {
            System.out.println(FILE_READ_OR_WRITE_ERROR_MESSAGE + e.getMessage());
        }
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
}

