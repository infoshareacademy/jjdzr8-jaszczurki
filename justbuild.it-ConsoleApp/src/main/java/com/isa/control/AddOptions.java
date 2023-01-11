package com.isa.control;

import com.isa.control.filesFactory.MyObjectFileStorage;
import com.isa.control.filesFactory.MyObjectParser;
import com.isa.entity.Offer;
import com.isa.entity.User;
import com.isa.entity.enums.ServiceCategory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.isa.entity.appConstants.AppConstants.*;

public class AddOptions extends SubMenuNavigator{
    private static final String ADD = "Tu możesz dodać swoje ogłoszenie";
    public static final String CATEGORY_SELECTION_MESSAGE = "Podaj 1 kategorię z dostępnych -> Budowa, Remont, Instalacje, Elektryka, Roboty ziemne, Ogród : ";
    private static final String OFFER_CONTENT_MESSAGE = "Podaj treść oferty: ";
    private static final String LOCALIZATION_MESSAGE = "Podaj swoją lokalizację: ";
    private static final String FIRST_NAME_MESSAGE = "Podaj imię: ";
    private static final String LAST_NAME_MESSAGE = "Podaj nazwisko: ";
    private static final String COMPANY_NAME_MESSAGE = "Podaj nazwę firmy: ";
    private static final String EMAIL_MESSAGE = "Podaj adres e-mail: ";
    private static final String PHONE_NUMBER_MESSAGE = "Podaj numer telefonu: ";
    public static final String USERS_OFFER_DISPLAY_MESSAGE = "Twoja oferta wygląda następująco: \n";
    public static final String USERS_OFFER_SAVING_MESSAGE = "Twoja oferta została pomyślnie zapisana. Numer Twojej oferty: ";
    private final MyObjectFileStorage fileStorage;

    public AddOptions() {
        fileStorage = new MyObjectFileStorage(new MyObjectParser());
    }

    public void showAddDetails(){

        System.out.println(ADD);
        System.out.println(ACCEPT_OR_BACK_TO_MENU_MESSAGE);
        subMenuActions();
    }
    @Override
    void subMenuActions() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.equals("1")) {
            Offer offer = new Offer();

            long offerID = getUniqueOfferID();
            offer.setOfferID(offerID);

            System.out.println(CHOOSE_A_NUMBER_MESSAGE);
            System.out.println(CATEGORY_SELECTION_MESSAGE);
            for (ServiceCategory d : ServiceCategory.values()){
                System.out.println(d.toString());
            }

            String serviceCategory = scanner.nextLine();
            boolean inProgress = true;

            while (inProgress) {
                switch (serviceCategory) {
                    case "1" -> {
                        offer.setServiceCategory(ServiceCategory.CONSTRUCTION);
                        inProgress = false;
                    }
                    case "2" -> {
                        offer.setServiceCategory(ServiceCategory.FINISHING_WORKS);
                        inProgress = false;
                    }
                    case "3" -> {
                        offer.setServiceCategory(ServiceCategory.INSTALLATION);
                        inProgress = false;
                    }
                    case "4" -> {
                        offer.setServiceCategory(ServiceCategory.ELECTRICITY);
                        inProgress = false;
                    }
                    case "5" -> {
                        offer.setServiceCategory(ServiceCategory.EARTH_WORKS);
                        inProgress = false;
                    }
                    case "6" -> {
                        offer.setServiceCategory(ServiceCategory.GARDEN);
                        inProgress = false;
                    }
                    default -> {
                        System.out.println(ENTERED_WRONG_CATEGORY_MESSAGE);
                        serviceCategory = scanner.nextLine().toLowerCase();
                    }
                }
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

            LocalDateTime localDateTime = LocalDateTime.now();
            offer.setDate(Date.from(Instant.parse(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toString())));

            Path filePath = Paths.get(OFFERS_FILEPATH);
            if(Files.exists(filePath)) {
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

            System.out.println(USERS_OFFER_DISPLAY_MESSAGE + offer);
            System.out.println(USERS_OFFER_SAVING_MESSAGE + offer.getOfferID());

            goBackToMenu();
        } else {
            goBackToMenu();
        }
    }

    private long getUniqueOfferID() {
        List<Offer> objects = new LinkedList<>();
        try {
            objects = fileStorage.readFromFile(OFFERS_FILEPATH);
        } catch (IOException e) {
            System.out.println(FILE_READ_OR_WRITE_ERROR_MESSAGE + e.getMessage());
        }

        long maxID = 0;

            for (Offer obj: objects) {
                if (obj != null) {
                    if (obj.getOfferID() > maxID) {
                    maxID = obj.getOfferID();
                }
            }
        }
        return maxID + 1;
    }
}
