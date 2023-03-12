package justbuild.it.web.app.service;

import justbuild.it.web.app.dto.OfferDto;
import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.mapper.OfferMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OfferService {

    private final OfferCreationService offerCreationService;
    private final OfferSearchingService offerSearchingService;

    public OfferService(OfferCreationService offerCreationService, OfferSearchingService offerSearchingService) {
        this.offerCreationService = offerCreationService;
        this.offerSearchingService = offerSearchingService;
    }

    public void addOffer(Offer offer) {
        offerCreationService.addOffer(offer);
    }

    public OfferDto provideNewOffer() {
        OfferDto offer = new OfferDto();
        offer.setDtoOfferId(offerCreationService.getNextOfferId());
        return offer;
    }

    public List<OfferDto> provideNewFilteredOfferDtoList(String searchValue) {
        OfferMapper offerMapper = new OfferMapper();
        List<OfferDto> filteredOfferDtoList;
        filteredOfferDtoList = offerMapper.toDtoList(offerSearchingService.getOffersListFilteredBySearchValue(searchValue));
        return filteredOfferDtoList;
    }

    public List<OfferDto> provideAllDtoList() {
        OfferMapper offerMapper = new OfferMapper();
        List<OfferDto> allOfferDtoList;
        allOfferDtoList = offerMapper.toDtoList(offerSearchingService.getOffersList());
        return allOfferDtoList;
    }
}