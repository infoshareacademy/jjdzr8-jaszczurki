package com.isa.entity;

import com.isa.control.filesFactory.MyObjectFileStorage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static com.isa.entity.appConstants.AppConstants.FILE_READ_OR_WRITE_ERROR_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.OFFERS_FILEPATH;

public class OfferArrayFromFile {

    private static final List<Offer> offersArray = new ArrayList<>();

    public static void setOffersArray() {
        MyObjectFileStorage fileStorage = new MyObjectFileStorage();
        try {
            if (Files.exists(Paths.get(OFFERS_FILEPATH))) {
                List<Offer> offersList = fileStorage.readFromFile(OFFERS_FILEPATH);
                offersArray.addAll(offersList);
            } else {
                new File(OFFERS_FILEPATH);
            }

        } catch (IOException e) {
            System.out.println(FILE_READ_OR_WRITE_ERROR_MESSAGE + e.getMessage());
        }
    }

    public static List<Offer> getOffersArray() {
        offersArray.sort((object1, object2) -> object2.getDate().compareTo(object1.getDate()));
        return offersArray;
    }
}

