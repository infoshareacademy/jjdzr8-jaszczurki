package com.isa.entity;

import com.isa.control.filesFactory.MyObjectFileStorage;
import com.isa.control.filesFactory.MyObjectParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.isa.entity.appConstants.AppConstants.FILE_READ_OR_WRITE_ERROR_MESSAGE;
import static com.isa.entity.appConstants.AppConstants.OFFERS_FILEPATH;

public class OfferArrayFromFile {

    private static final ArrayList<Offer> offersArray = new ArrayList<>();

    public static void setOffersArray() {
        MyObjectFileStorage fileStorage = new MyObjectFileStorage(new MyObjectParser());
        try {
            List<Offer> offersList = fileStorage.readFromFile(OFFERS_FILEPATH);
            offersArray.addAll(offersList);
        } catch (IOException e) {
            System.out.println(FILE_READ_OR_WRITE_ERROR_MESSAGE + e.getMessage());
        }
    }

    public static ArrayList<Offer> getOffersArray() {
        Collections.reverse(offersArray);
        return offersArray;
    }
}
