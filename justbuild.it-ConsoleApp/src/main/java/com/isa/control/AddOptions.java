package com.isa.control;

import com.isa.control.filesFactory.MyObjectFileStorage;
import com.isa.control.filesFactory.MyObjectParser;
import com.isa.entity.Offer;
import com.isa.entity.User;
import com.isa.entity.enums.ServiceCategory;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import static com.isa.entity.appConstants.AppConstants.*;

public class AddOptions extends SubMenuNavigator{
    private static final String ADD = "Tu możesz dodać ogłoszenie";
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
    protected void subMenuActions() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.equals("1")) {
            Offer offer = new Offer();

            long offerID = getUniqueOfferID();
            offer.setOfferID(offerID);

            System.out.println(CATEGORY_SELECTION_MESSAGE);
            String serviceCategory = scanner.nextLine().toLowerCase();
            switch (serviceCategory) {
                case "budowa" -> offer.setServiceCategory(ServiceCategory.CONSTRUCTION);
                case "remont" -> offer.setServiceCategory(ServiceCategory.FINISHING_WORKS);
                case "instalacje" -> offer.setServiceCategory(ServiceCategory.INSTALLATION);
                case "elektryka" -> offer.setServiceCategory(ServiceCategory.ELECTRICITY);
                case "roboty ziemne" -> offer.setServiceCategory(ServiceCategory.EARTH_WORKS);
                case "ogród" -> offer.setServiceCategory(ServiceCategory.GARDEN);
                default -> {
                    System.out.println(ENTERED_WRONG_CATEGORY_MESSAGE);
                    goBackToMenu();
                }
            }

            System.out.println("Podaj treść oferty: ");
            offer.setOfferContent(scanner.nextLine());

            System.out.println("Podaj lokalizację: ");
            offer.setCity(scanner.nextLine());

            System.out.println("Podaj imię: ");
            String firstName = scanner.nextLine();

            System.out.println("Podaj nazwisko: ");
            String lastName = scanner.nextLine();

            System.out.println("Podaj nazwę firmy: ");
            String companyName = scanner.nextLine();

            System.out.println("Podaj adres email: ");
            String email = scanner.nextLine();

            System.out.println("Podaj numer telefonu: ");
            String phoneNumber = scanner.nextLine();

            offer.setUser(new User(firstName, lastName, companyName, email, phoneNumber));

            LocalDateTime localDateTime = LocalDateTime.now();
            offer.setDate(Date.from(Instant.parse(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toString())));
            System.out.println(USERS_OFFER_DISPLAY_MESSAGE + offer);

            try {
                List<Offer> offersList = fileStorage.readFromFile(OFFERS_FILEPATH);
                offersList.add(offer);
                fileStorage.saveToFile(offersList, OFFERS_FILEPATH);
            } catch (IOException e) {
                System.out.println(FILE_READ_OR_WRITE_ERROR_MESSAGE + e.getMessage());
            }

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
