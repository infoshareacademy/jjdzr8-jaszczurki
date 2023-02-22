package justbuild.it.web.app.service;

import justbuild.it.web.app.model.Offer;
import justbuild.it.web.app.repository.OfferFileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OfferCreationService implements OfferCreationServiceInterface {
    private final OfferFileRepository offerFileRepository;

    public OfferCreationService(OfferFileRepository offerFileRepository) {
        this.offerFileRepository = offerFileRepository;
    }

    @Override
    public void addOffer(Offer offer) {
        List<Offer> offers = offerFileRepository.getOffersFromJsonFile("offers.json");
        offers.add(offer);
        offerFileRepository.saveOffersToJsonFile(offers);

    }

    @Override
    public Long getNextOfferId() {
        List<Offer> offers = offerFileRepository.getOffersFromJsonFile("offers.json");

        long lastOfferId = Objects.requireNonNull(offers).stream()
                .mapToLong(Offer::getOfferID)
                .max()
                .orElse(0L);
        return lastOfferId + 1;
    }
}
