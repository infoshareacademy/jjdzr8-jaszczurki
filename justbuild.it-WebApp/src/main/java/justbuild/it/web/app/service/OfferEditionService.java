package justbuild.it.web.app.service;

import justbuild.it.web.app.dto.OfferDto;
import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.mapper.OfferMapper;
import justbuild.it.web.app.repository.OfferFileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static justbuild.it.web.app.entity.constants.AppConstants.OFFERS_FILEPATH;

@Service
class OfferEditionService implements OfferEditionServiceInterface {

    private final OfferFileRepository offerFileRepository;
    private final OfferMapper mapper;

    OfferEditionService(OfferFileRepository offerFileRepository, OfferMapper mapper) {
        this.offerFileRepository = offerFileRepository;
        this.mapper = mapper;
    }

    public OfferDto getOfferDtoById(Long id) {

        return offerFileRepository.findOfferById(id)
                .map(mapper::toDto)
                .orElseThrow();
    }

    public void updateOffer(OfferDto editedOfferDto) {
        Offer editedOffer = mapper.fromDto(editedOfferDto);
        List<Offer> offers = offerFileRepository.getOffersFromJsonFile(OFFERS_FILEPATH);
        Offer toReplaceOffer = offers.stream().filter(offer -> offer.getOfferId().equals(editedOffer.getOfferId())).findFirst().orElseThrow();
        offers.remove(toReplaceOffer);
        offers.add(editedOffer);
        offerFileRepository.saveOffersToJsonFile(offers, OFFERS_FILEPATH);
    }
}
