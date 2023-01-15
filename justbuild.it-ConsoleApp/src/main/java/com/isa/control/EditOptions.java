package com.isa.control;

import com.isa.control.filesFactory.MyObjectFileStorage;
import com.isa.entity.Offer;
import com.isa.entity.OfferArrayFromFile;
import com.isa.entity.User;
import com.isa.entity.enums.ServiceCategory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.isa.entity.appConstants.AppConstants.*;

public class EditOptions extends SubMenuNavigator{
    private final Scanner scanner;
    private final MyObjectFileStorage fileStorage;
    public static final String EDIT = "Edytuj ogłoszenie.";

    public EditOptions() {
        this.scanner = new Scanner(System.in);
        fileStorage = new MyObjectFileStorage();
    }

    public void showEditDetails(){

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

        long numberOfferToEdit;
        Offer offer = null;

        while (offer == null) {
            try {
                System.out.println("Podaj numer oferty, którą chcesz zmienić: ");
                numberOfferToEdit = scanner.nextLong();
                if (numberOfferToEdit > 0 && numberOfferToEdit <= OfferArrayFromFile.getOffersArray().size()) {
                    offer = getOfferByNumber(numberOfferToEdit);
                    if (offer == null) {
                        System.out.println("Oferta o podanym numerze nie istnieje. Spróbuj ponownie lub wprowadź 0, aby powrócić do głównego menu.");
                        numberOfferToEdit = scanner.nextLong();
                        if (numberOfferToEdit == 0) {
                            goBackToMenu();
                            subMenuActions();
                        }
                    } else {
                        System.out.println("Informacje o aktualnej ofercie: \n");
                        System.out.println(offer.printOffer());
                        System.out.println("\nPodaj nowe informacje o ofercie (pozostawienie pustego pola oznacza brak zmian): ");
                    }
                } else {
                    System.out.println("Numer oferty poza zakresem. Spróbuj ponownie lub wprowadź 0, aby powrócić do głównego menu.");
                    numberOfferToEdit = scanner.nextLong();
                    if (numberOfferToEdit == 0) {
                        goBackToMenu();
                        subMenuActions();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Nie podano numeru oferty. Spróbuj ponownie lub wprowadź 0, aby powrócić do głównego menu.");
                scanner.nextLine();
                numberOfferToEdit = scanner.nextLong();
                if (numberOfferToEdit == 0) {
                    goBackToMenu();
                    subMenuActions();
                }
            }
            scanner.nextLine();

            System.out.println("Zmień swoją kategorię usługi: ");
            String serviceCategoryNumber = scanner.nextLine();
            boolean inProgress = true;
            if (!serviceCategoryNumber.isEmpty()) {
                while (inProgress) {
                    try {
                        ServiceCategory category = ServiceCategory.getFromString(serviceCategoryNumber);
                        if (offer != null) {
                            offer.setServiceCategory(category);
                            inProgress = false;
                        }
                        inProgress = false;
                    } catch (IllegalArgumentException e) {
                        System.out.println(ENTERED_WRONG_CATEGORY_MESSAGE);
                        serviceCategoryNumber = scanner.nextLine();
                    }
                }
            }

            System.out.println("Zmień treść oferty: ");
            String offerContent = scanner.nextLine().toLowerCase();
            if (!offerContent.isEmpty()) {
                if (offer != null) {
                    offer.setOfferContent(offerContent);
                }
            }

            System.out.println("Zmień swoją lokalizację: ");
            String city = scanner.nextLine().toLowerCase();
            if(!city.isEmpty()) {
                if (offer != null) {
                    offer.setCity(city);
                }
            }

            User modificatedUser;
            if (offer != null) {
                modificatedUser = offer.getUser();

                System.out.println("Zmień imię: ");
                String firstName = scanner.nextLine();
                if (!firstName.isEmpty()) modificatedUser.setFirstName(firstName);

                System.out.println("Zmień nazwisko: ");
                String lastName = scanner.nextLine();
                if (!lastName.isEmpty()) modificatedUser.setLastName(lastName);

                System.out.println("Zmień nazwę firmy: ");
                String companyName = scanner.nextLine();
                if (!companyName.isEmpty()) modificatedUser.setCompany(companyName);

                System.out.println("Zmień adres e-mail: ");
                String email = scanner.nextLine();
                if (!email.isEmpty()) modificatedUser.setEmailAddress(email);

                System.out.println("Zmień numer telefonu: ");
                String phoneNumber = scanner.nextLine();
                if (!phoneNumber.isEmpty()) modificatedUser.setTelephoneNumber(phoneNumber);
            }

            System.out.println("Czy na pewno chcesz zapisać zmiany? (tak/nie)");
            String confirm = scanner.nextLine();
            if(confirm.equalsIgnoreCase("tak")){
                if (offer != null) {
                    offer.setDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
                }
                if (offer != null) {
                    System.out.println("Oferta zmieniona: " + offer.getDate());
                }
                saveOfferChanges(offer);
                System.out.println("Zmiany zapisano.");
            }else{
                System.out.println("Anulowano zmiany.");
            }
            goBackToMenu();
        }
    }

    private Offer getOfferByNumber(long number) {
        List<Offer> offersList = OfferArrayFromFile.getOffersArray();
        for (Offer offer: offersList) {
            if (Objects.equals(offer.getOfferID(), number)) {
                return offer;
            }
        }
        return null;
    }

    private void saveOfferChanges(Offer offer){
        try {
            List<Offer> offersList = OfferArrayFromFile.getOffersArray();
            for (int i = 0; i < offersList.size(); i++) {
                if (Objects.equals(offersList.get(i).getOfferID(), offer.getOfferID())) {
                    offersList.set(i, offer);
                    break;
                }
            }
            fileStorage.saveToFile(offersList, OFFERS_FILEPATH);
        } catch (IOException e) {
            System.out.println(FILE_READ_OR_WRITE_ERROR_MESSAGE + e.getMessage());
        }
    }
}

