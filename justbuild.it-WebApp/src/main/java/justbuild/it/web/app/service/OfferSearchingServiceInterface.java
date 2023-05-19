package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.Offer;

import java.util.List;

public interface OfferSearchingServiceInterface {
    List<Offer> getOffersList();

    List<Offer> getOffersListFilteredBySearchValue(String searchValue);

    List<Offer> getOffersListFilteredByCategory(String category);
}