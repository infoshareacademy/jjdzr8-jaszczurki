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
    public static final String OFFER_NUMBER_TO_CHANGE_MESSAGE = "Podaj numer oferty, którą chcesz zmienić: ";
    public static final String MISMATCHED_OFFER_NUMBER_MESSAGE = "Nie podano numeru oferty. Spróbuj ponownie.";
    public static final String WRONG_OFFER_NUMBER_MESSAGE = "Oferta o podanym numerze nie istnieje. Spróbuj ponownie.";
    public static final String OFFER_INFO_MESSAGE = "Informacje o aktualnej ofercie: \n";
    public static final String OFFER_READY_TO_EDIT_MESSAGE = "\nPodaj nowe informacje o ofercie (pozostawienie pustego pola oznacza brak zmian): ";
    public static final String CATEGORY_READY_TO_EDIT_MESSAGE = "Zmień swoją kategorię usługi: ";
    public static final String OFFER_CONTENT_READY_TO_EDIT_MESSAGE = "Zmień treść oferty: ";
    public static final String LOCALIZATION_READY_TO_EDIT_MESSAGE = "Zmień swoją lokalizację (nazwa miasta): ";
    public static final String FIRST_NAME_READY_TO_EDIT_MESSAGE = "Zmień imię: ";
    public static final String LAST_NAME_READY_TO_EDIT_MESSAGE = "Zmień nazwisko: ";
    public static final String COMPANY_NAME_READY_TO_EDIT_MESSAGE = "Zmień nazwę firmy: ";
    public static final String EMAIL_READY_TO_EDIT_MESSAGE = "Zmień adres e-mail: ";
    public static final String PHONE_NUMBER_READY_TO_EDIT_MESSAGE = "Zmień numer telefonu: ";
    public static final String CHANGES_CONFIRMATION_MESSAGE = "Czy na pewno chcesz zapisać zmiany? (tak)";
    public static final String OFFER_CHANGED_MESSAGE = "Oferta zmieniona: ";
    public static final String CHANGES_SAVED_MESSAGE = "Zmiany zapisano.";
    public static final String CHANGES_CANCELED_MESSAGE = "Anulowano zmiany.";

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
        boolean isOfferValid = false;
        long numberOfferToEdit;
        Offer offer = null;

        while (!isOfferValid) {
            try {
                System.out.println(OFFER_NUMBER_TO_CHANGE_MESSAGE);
                numberOfferToEdit = scanner.nextLong();
                offer = getOfferByNumber(numberOfferToEdit);
                isOfferValid = true;
            } catch (InputMismatchException e) {
                System.out.println(MISMATCHED_OFFER_NUMBER_MESSAGE);
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
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
        if(!city.isEmpty()) {
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
        if(confirm.equalsIgnoreCase("tak")){
            offer.setDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            System.out.println(OFFER_CHANGED_MESSAGE + offer.getDate());
            saveOfferChanges(offer);
            System.out.println(CHANGES_SAVED_MESSAGE);
        }else{
            System.out.println(CHANGES_CANCELED_MESSAGE);
        }
        goBackToMenu();
    }

    private Offer getOfferByNumber(long numberOfferToEdit) {
        Offer offer = null;
        for (Offer offerNumber : OfferArrayFromFile.getOffersArray()) {
            if (offerNumber.getOfferID() == numberOfferToEdit) {
                offer = offerNumber;
                break;
            }
        }
        if (offer == null) {
            throw new IllegalArgumentException(WRONG_OFFER_NUMBER_MESSAGE);
        }
        return offer;
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

