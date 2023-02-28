package justbuild.it.web.app.service;

import justbuild.it.web.app.dto.OfferDto;
import justbuild.it.web.app.entity.Offer;
import org.springframework.stereotype.Service;

@Service
public class OfferService {

    private final OfferCreationService offerCreationService;

    public OfferService(OfferCreationService offerCreationService) {
        this.offerCreationService = offerCreationService;
    }

    public void addOffer(Offer offer) {
        offerCreationService.addOffer(offer);
    }

    public OfferDto provideNewOffer() {
        OfferDto offer = new OfferDto();
        offer.setDtoOfferId(offerCreationService.getNextOfferId());
        return offer;
    }
}
