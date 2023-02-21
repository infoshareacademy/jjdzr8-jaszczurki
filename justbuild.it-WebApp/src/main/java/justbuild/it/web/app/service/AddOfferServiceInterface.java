package justbuild.it.web.app.service;

import justbuild.it.web.app.model.Offer;
import org.springframework.stereotype.Service;

@Service
public interface AddOfferServiceInterface {

    void addOffer(Offer offer);

    Long getNextOfferId();
}
