package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.repository.OfferFileRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import static justbuild.it.web.app.entity.constants.AppConstants.OFFERS_FILEPATH;

@Service
public class OfferSearchingService implements OfferSearchingServiceInterface {

    private final OfferFileRepository offerFileRepository;

    public OfferSearchingService(OfferFileRepository offerFileRepository) {
        this.offerFileRepository = offerFileRepository;
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