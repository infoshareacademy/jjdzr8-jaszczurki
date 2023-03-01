package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.Offer;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
interface OfferCreationServiceInterface {

    void addOffer(Offer offer);

    Long getNextOfferId();

    List<Offer> getOffersList();

    List<Offer> getOffersListFilteredBySearchValue(String searchValue);
}