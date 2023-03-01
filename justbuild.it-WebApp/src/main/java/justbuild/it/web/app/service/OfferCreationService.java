package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.repository.OfferFileRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import static justbuild.it.web.app.entity.constants.AppConstants.OFFERS_FILEPATH;

@Service
class OfferCreationService implements OfferCreationServiceInterface {

    private final OfferFileRepository offerFileRepository;

    OfferCreationService(OfferFileRepository offerFileRepository) {
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
                .mapToLong(Offer::getOfferId)
                .reduce(0L, Long::max)
                + 1L;
    }

    @Override
    public List<Offer> getOffersList() {
        return offerFileRepository.getOffersFromJsonFile(OFFERS_FILEPATH);
    }

    @Override
    public List<Offer> getOffersListFilteredBySearchValue(String searchValue) {
        List<Offer> offers = offerFileRepository.getOffersFromJsonFile(OFFERS_FILEPATH);

        return offers.stream()
                .filter(value -> value.getOfferContent().toLowerCase().contains(searchValue.toLowerCase())
                        || value.getOfferId().toString().contains(searchValue)
                        || value.getCity().toLowerCase().contains(searchValue.toLowerCase())
                        || value.getUser().getCompany().toLowerCase().contains(searchValue.toLowerCase())
                        || value.getUser().getLastName().toLowerCase().contains(searchValue.toLowerCase())
                        || value.getServiceCategory().toString().toLowerCase().contains(searchValue.toLowerCase()))
                .collect(Collectors.toList());
    }
}