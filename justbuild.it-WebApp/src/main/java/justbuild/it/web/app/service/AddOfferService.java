package justbuild.it.web.app.service;

import justbuild.it.web.app.model.Offer;
import justbuild.it.web.app.repository.OfferFileRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class AddOfferService implements AddOfferServiceInterface {
    private final OfferFileRepository offerFileRepository;

    public AddOfferService(OfferFileRepository offerFileRepository) {
        this.offerFileRepository = offerFileRepository;
    }

    @Override
    public void addOffer(Offer offer) {
        try {
            List<Offer> offers = offerFileRepository.getOffersFromJsonFile("offers.json");
            offers.add(offer);
            offerFileRepository.saveOffersToJsonFile(offers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long getNextOfferId() {
        List<Offer> offers = null;
        try {
            offers = offerFileRepository.getOffersFromJsonFile("offers.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        long lastOfferId = Objects.requireNonNull(offers).stream()
                .mapToLong(Offer::getOfferID)
                .max()
                .orElse(0L);
        return lastOfferId + 1;
    }
}
