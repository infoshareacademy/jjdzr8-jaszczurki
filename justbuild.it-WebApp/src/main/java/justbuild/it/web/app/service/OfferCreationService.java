package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.repository.OfferFileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static justbuild.it.web.app.entity.constants.AppConstants.OFFERS_FILEPATH;

@Service
class OfferCreationService implements OfferCreationServiceInterface {

    private final OfferFileRepository offerFileRepository;
    private static final String OFFERS_LIST_CANNOT_BE_NULL_MESSAGE = "list of offers cannot be null";

    OfferCreationService(OfferFileRepository offerFileRepository) {
        this.offerFileRepository = offerFileRepository;
    }

    @Override
    public void addOffer(Offer offer) {
        List<Offer> offers = offerFileRepository.getOffersFromJsonFile(OFFERS_FILEPATH);
        if (offers == null) {
            offers = new ArrayList<>();
        }
        offers.add(offer);
        offerFileRepository.saveOffersToJsonFile(offers, OFFERS_FILEPATH);
    }

    @Override
    public Long getNextOfferId() {
        List<Offer> offers = offerFileRepository.getOffersFromJsonFile(OFFERS_FILEPATH);

        return Objects.requireNonNull(offers, OFFERS_LIST_CANNOT_BE_NULL_MESSAGE)
                .stream()
                .mapToLong(Offer::getOfferId)
                .reduce(0L, Long::max)
                + 1L;
    }
}