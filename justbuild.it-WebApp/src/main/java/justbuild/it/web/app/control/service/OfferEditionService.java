package justbuild.it.web.app.control.service;

import justbuild.it.web.app.entity.dto.OfferDto;
import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.control.mapper.OfferMapper;
import justbuild.it.web.app.control.repository.OfferFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static justbuild.it.web.app.entity.constants.AppConstants.OFFERS_FILEPATH;

@Service
class OfferEditionService implements OfferEditionServiceInterface {

    private final OfferFileRepository offerFileRepository;
    private final OfferMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferEditionService.class);

    OfferEditionService(OfferFileRepository offerFileRepository, OfferMapper mapper) {
        this.offerFileRepository = offerFileRepository;
        this.mapper = mapper;
    }

    public OfferDto getOfferDtoById(Long id) {
        LOGGER.debug("Fetching offer with ID: {}", id);
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
        LOGGER.debug("Offer with ID: {} has been updated", editedOffer.getOfferId());
    }
}
