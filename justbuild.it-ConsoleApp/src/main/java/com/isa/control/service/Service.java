package com.isa.control.service;

import com.isa.control.filesFactory.MyObjectFileStorage;
import com.isa.entity.Offer;
import com.isa.entity.OfferArrayFromFile;
import com.isa.entity.enums.ServiceCategory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static com.isa.entity.appConstants.AppConstants.ENTERED_WRONG_CATEGORY_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.FILE_READ_OR_WRITE_ERROR_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.NO_OFFERS_MORE;
import static com.isa.entity.appConstants.AppConstants.OFFERS_FILEPATH;

public class Service {

    private static final String CATEGORY_FOR_SEARCH_SELECTION_MESSAGE = "Musisz podać przynajmniej 1 kategorię z dostępnych poniżej i wcisnąć \"Enter\". \nNastępnie wpisz słowo \"koniec\" żeby zakończyć wybór kategorii. Możesz podać dowolną ilość kategorii: ";
    private static final String ALREADY_CHOSEN_CATEGORY_MESSAGE = "Wybrano juź tę kategorię. Proszę wybrać inną.";

    MyObjectFileStorage myObjectFileStorage = new MyObjectFileStorage();
    Scanner scanner = new Scanner(System.in);

    public long getUniqueOfferID() {
        List<Offer> objects = new LinkedList<>();
        try {
            objects = myObjectFileStorage.readFromFile(OFFERS_FILEPATH);
        } catch (IOException e) {
            System.out.println(FILE_READ_OR_WRITE_ERROR_MESSAGE + e.getMessage());
        }

        long maxID = 0;

        for (Offer item : objects) {
            if (item != null) {
                if (item.getOfferID() > maxID) {
                    maxID = item.getOfferID();
                }
            }

        }
        return maxID + 1;
    }

    public boolean deleteOfferFromFile(Offer offer) {

        List<Offer> offersList = OfferArrayFromFile.getOffersArray();
        boolean removed = offersList.remove(offer);
        if (removed) {
            try {
                myObjectFileStorage.saveToFile(offersList, OFFERS_FILEPATH);
            } catch (IOException e) {
                System.out.println(FILE_READ_OR_WRITE_ERROR_MESSAGE + e.getMessage());
            }
        }
        return removed;
    }

    public void saveOfferChanges(Offer offer) {
        try {
            List<Offer> offersList = OfferArrayFromFile.getOffersArray();
            for (int i = 0; i < offersList.size(); i++) {
                if (Objects.equals(offersList.get(i).getOfferID(), offer.getOfferID())) {
                    offersList.set(i, offer);
                    break;
                }
            }
            myObjectFileStorage.saveToFile(offersList, OFFERS_FILEPATH);
        } catch (IOException e) {
            System.out.println(FILE_READ_OR_WRITE_ERROR_MESSAGE + e.getMessage());
        }
    }

    public List<ServiceCategory> selectServiceCategories() {
        List<ServiceCategory> selectedCategories = new ArrayList<>();
        displayServiceCategories();
        while (true) {
            String serviceCategoryNumber = scanner.nextLine();
            if (serviceCategoryNumber.equals("koniec")) {
                if (!selectedCategories.isEmpty()) {
                    break;
                }
            } else {
                ServiceCategory category = convertInputToCategory(serviceCategoryNumber);
                if (category != null) {
                    if (isCategoryAlreadySelected(selectedCategories, category)) {
                        System.out.println(ALREADY_CHOSEN_CATEGORY_MESSAGE);
                    } else {
                        selectedCategories.add(category);
                    }
                } else {
                    System.out.println(ENTERED_WRONG_CATEGORY_MESSAGE);
                }
            }
        }
        return selectedCategories;
    }

    private void displayServiceCategories() {
        System.out.println(CATEGORY_FOR_SEARCH_SELECTION_MESSAGE);
        for (ServiceCategory serviceCategory : ServiceCategory.values()) {
            System.out.println(serviceCategory);
        }
    }

    private boolean isCategoryAlreadySelected(List<ServiceCategory> selectedCategories, ServiceCategory category) {
        return selectedCategories.contains(category);
    }

    private ServiceCategory convertInputToCategory(String serviceCategoryNumber) {
        try {
            return ServiceCategory.getFromString(serviceCategoryNumber);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Offer getOfferByNumber(long numberOfferToEdit) {
        Offer offer = null;
        for (Offer offerNumber : OfferArrayFromFile.getOffersArray()) {
            if (offerNumber.getOfferID() == numberOfferToEdit) {
                offer = offerNumber;
                break;
            }
        }
        return offer;
    }


    public void findOfferCategory(ServiceCategory serviceCategory) {
        for (Offer offer : OfferArrayFromFile.getOffersArray()) {
            if (offer.getServiceCategory().equals(serviceCategory)) {
                System.out.println(offer);
            }
        }
        System.out.println(NO_OFFERS_MORE);
    }
}
