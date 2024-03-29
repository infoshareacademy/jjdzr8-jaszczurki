package com.isa.control;

import com.isa.control.filesFactory.MyObjectFileStorage;
import com.isa.control.service.Service;
import com.isa.entity.Offer;
import com.isa.entity.OfferArrayFromFile;
import com.isa.entity.User;
import com.isa.entity.enums.ServiceCategory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static com.isa.entity.appConstants.AppConstants.ACCEPT_OR_BACK_TO_MENU_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.CHOOSE_A_NUMBER_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.ENTERED_WRONG_CATEGORY_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.FILE_READ_OR_WRITE_ERROR_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.OFFERS_FILEPATH;

public class AddOptions extends SubMenuNavigator {
    private static final String ADD = "Tu możesz dodać swoje ogłoszenie";
    private static final String CATEGORY_SELECTION_MESSAGE = "Podaj 1 kategorię z dostępnych -> Budowa, Remont, Instalacje, Elektryka, Roboty ziemne, Ogród : ";
    private static final String OFFER_CONTENT_MESSAGE = "Podaj treść oferty: ";
    private static final String LOCALIZATION_MESSAGE = "Podaj swoją lokalizację (nazwa miasta): ";
    private static final String FIRST_NAME_MESSAGE = "Podaj imię: ";
    private static final String LAST_NAME_MESSAGE = "Podaj nazwisko: ";
    private static final String COMPANY_NAME_MESSAGE = "Podaj nazwę firmy: ";
    private static final String EMAIL_MESSAGE = "Podaj adres e-mail: ";
    private static final String PHONE_NUMBER_MESSAGE = "Podaj numer telefonu: ";
    private static final String USERS_OFFER_DISPLAY_MESSAGE = "Twoja oferta wygląda następująco: \n";
    private static final String USERS_OFFER_SAVING_MESSAGE = "Twoja oferta została pomyślnie zapisana. Numer Twojej oferty: ";
    private final MyObjectFileStorage fileStorage;

    public AddOptions() {
        fileStorage = new MyObjectFileStorage();
    }

    public void showAddDetails() {

        System.out.println(ADD);
        System.out.println(ACCEPT_OR_BACK_TO_MENU_MESSAGE);
        subMenuActions();
    }

    Service service = new Service();

    @Override
    void subMenuActions() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.equals("1")) {
            Offer offer = new Offer();

            long offerID = service.getUniqueOfferId();
            offer.setOfferId(offerID);

            System.out.println(CHOOSE_A_NUMBER_MESSAGE);
            System.out.println(CATEGORY_SELECTION_MESSAGE);
            for (ServiceCategory serviceCategory : ServiceCategory.values()) {
                System.out.println(serviceCategory.toString());
            }

            String serviceCategoryNumber = scanner.nextLine();
            boolean inProgress = true;
            while (inProgress) {
                try {
                    ServiceCategory category = ServiceCategory.getFromString(serviceCategoryNumber);
                    offer.setServiceCategory(category);
                    inProgress = false;
                } catch (IllegalArgumentException e) {
                    System.out.println(ENTERED_WRONG_CATEGORY_MESSAGE);
                }
                if (inProgress) serviceCategoryNumber = scanner.nextLine();
            }

            System.out.println(OFFER_CONTENT_MESSAGE);
            offer.setOfferContent(scanner.nextLine().toLowerCase());

            System.out.println(LOCALIZATION_MESSAGE);
            offer.setCity(scanner.nextLine().toLowerCase());

            System.out.println(FIRST_NAME_MESSAGE);
            String firstName = scanner.nextLine();

            System.out.println(LAST_NAME_MESSAGE);
            String lastName = scanner.nextLine();

            System.out.println(COMPANY_NAME_MESSAGE);
            String companyName = scanner.nextLine();

            System.out.println(EMAIL_MESSAGE);
            String email = scanner.nextLine();

            System.out.println(PHONE_NUMBER_MESSAGE);
            String phoneNumber = scanner.nextLine();

            offer.setUser(new User(firstName, lastName, companyName, email, phoneNumber));

            offer.setDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

            Path filePath = Paths.get(OFFERS_FILEPATH);
            if (Files.exists(filePath)) {
                try {
                    List<Offer> offersList = fileStorage.readFromFile(OFFERS_FILEPATH);
                    offersList.add(offer);
                    fileStorage.saveToFile(offersList, OFFERS_FILEPATH);
                } catch (IOException e) {
                    System.out.println(FILE_READ_OR_WRITE_ERROR_MESSAGE + e.getMessage());
                }
            } else {
                try {
                    Files.createFile(filePath);
                    List<Offer> offersList = new LinkedList<>();
                    offersList.add(offer);
                    fileStorage.saveToFile(offersList, OFFERS_FILEPATH);
                } catch (IOException e) {
                    System.out.println(FILE_READ_OR_WRITE_ERROR_MESSAGE + e.getMessage());
                }
            }

            OfferArrayFromFile.setOffersArray();

            System.out.println(USERS_OFFER_DISPLAY_MESSAGE + "\n" + offer.printOffer() + "\n");
            System.out.println(USERS_OFFER_SAVING_MESSAGE + offer.getOfferId());

            goBackToMenu();
        } else {
            goBackToMenu();
        }
    }
}
