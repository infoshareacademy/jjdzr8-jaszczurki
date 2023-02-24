package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.repository.OfferFileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static justbuild.it.web.app.config.constants.AppConstants.OFFERS_FILEPATH;

@Service
public class OfferCreationService implements OfferCreationServiceInterface {

    private final OfferFileRepository offerFileRepository;

    public OfferCreationService(OfferFileRepository offerFileRepository) {
        this.offerFileRepository = offerFileRepository;
    }

    @Override
    public void addOffer(Offer offer) {
        List<Offer> offers = offerFileRepository.getOffersFromJsonFile(OFFERS_FILEPATH);
        offers.add(offer);
        offerFileRepository.saveOffersToJsonFile(offers, OFFERS_FILEPATH);
    }

    @Override
    public Long getNextOfferId() {
        List<Offer> offers = offerFileRepository.getOffersFromJsonFile(OFFERS_FILEPATH);

        return offers.stream()
                .mapToLong(Offer::getOfferID)
                .reduce(0L, Long::max)
                + 1L;
    }
}
