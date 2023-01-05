package com.isa.control;


import com.isa.domain.Offer;
import com.isa.domain.User;
import com.isa.entity.enums.ServiceCategory;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static com.isa.entity.appConstants.AppConstants.ACCEPT_OR_BACK_TO_MENU;

public class AddOptions extends SubMenuNavigator{

    private static final String ADD = "Tu możesz dodać ogłoszenie";

    public void showAddDetails(){

        System.out.println(ADD);
        System.out.println(ACCEPT_OR_BACK_TO_MENU);
        subMenuActions();
    }
    @Override
    protected void subMenuActions() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.matches("1")) {
            Offer offer = new Offer();
            //long offerID = getUniqueOfferID(); <- metoda pobierająca pierwsze "wolne" ID dla oferty użytkownika

            //offer.setOfferID(offerID);

            System.out.println("Podaj ID oferty: ");
            offer.setOfferID(scanner.nextInt());
            scanner.nextLine();

            System.out.println("Podaj 1 kategorię z dostępnych -> Budowa, Remont, Instalacje, Elektryka, Roboty ziemne, Ogród : ");
            String serviceCategory = scanner.nextLine();
            if (serviceCategory.matches("Budowa")) {
                offer.setServiceCategory(ServiceCategory.CONSTRUCTION);
            } else if (serviceCategory.matches("Remont")) {
                offer.setServiceCategory(ServiceCategory.FINISHING_WORKS);
            } else if (serviceCategory.matches("Instalacje")) {
                offer.setServiceCategory(ServiceCategory.INSTALLATION);
            } else if (serviceCategory.matches("Elektryka")) {
                offer.setServiceCategory(ServiceCategory.ELECTRICITY);
            } else if (serviceCategory.matches("Roboty ziemne")) {
                offer.setServiceCategory(ServiceCategory.EARTH_WORKS);
            } else if (serviceCategory.matches("Ogród")) {
                offer.setServiceCategory(ServiceCategory.GARDEN);
            } else {
                System.out.println("Nieprawidłowa kategoria usługi. Spróbuj ponownie.");
                goBackToMenu();
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

            System.out.println("Twoja oferta wygląda następująco: \n" + offer);

            offer.setDate(new Date()); // to chyba ma być połączone z zapisywaniem do pliku

            //zapisanie oferty do pliku
            //saveOfferToFile(offer);
            goBackToMenu(); // to do usunięcia (chyba) po zaimplementowaniu metody zapisującej ofertę do pliku
        } else {
            goBackToMenu();
        }
    }

    //private long getUniqueOfferID() {
        long minID = 1;
        long maxID = 10000;

        //List<Offer> allOffers = getAllOffersFromFile(offersDB); <- pobieranie listy ofert z pliku

        //for (long i = minID; i <= maxID; i++) {
            boolean isIDOccupied = false;
            //for (Offer offer: allOffers) {
            //    if (offer.getOfferID() == i) {
            //        isIDOccupied = true;
            //        break;
            //    }
            //}
            //if (!isIDOccupied) {
            //    return i;
            //}
        //}
        //return 0;
    //}
}
