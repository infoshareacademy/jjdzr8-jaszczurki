package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.repository.OfferFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static justbuild.it.web.app.entity.constants.AppConstants.OFFERS_FILEPATH;

@Service
class OfferCreationService implements OfferCreationServiceInterface {

    private final OfferFileRepository offerFileRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferCreationService.class);

    OfferCreationService(OfferFileRepository offerFileRepository) {
        this.offerFileRepository = offerFileRepository;
    }

    @Override
    public void addOffer(Offer offer) {
        List<Offer> offers = offerFileRepository.getOffersFromJsonFile(OFFERS_FILEPATH);
        offers.add(offer);
        offerFileRepository.saveOffersToJsonFile(offers, OFFERS_FILEPATH);
        LOGGER.info("Adding offer with ID: {}", offer.getOfferId());
        LOGGER.debug("Adding offer '{}'", offer);
    }

    @Override
    public Long getNextOfferId() {
        List<Offer> offers = offerFileRepository.getOffersFromJsonFile(OFFERS_FILEPATH);

        long nextOfferId = offers.stream()
                .mapToLong(Offer::getOfferId)
                .reduce(0L, Long::max)
                + 1L;

        LOGGER.debug("Getting next offer ID: {}", nextOfferId);
        return nextOfferId;
    }
}