package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.Offer;
import org.springframework.stereotype.Service;

@Service
public interface OfferCreationServiceInterface {

    void addOffer(Offer offer);

    Long getNextOfferId();
}
