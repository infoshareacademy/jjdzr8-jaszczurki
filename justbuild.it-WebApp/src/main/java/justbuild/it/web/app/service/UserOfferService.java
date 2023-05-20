package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.repository.OfferFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static justbuild.it.web.app.entity.constants.AppConstants.OFFERS_FILEPATH;

@Service
public class UserOfferService implements UserOffersInterface {

    private final OfferFileRepository offerFileRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserOfferService.class);

    public UserOfferService(OfferFileRepository offerFileRepository) {
        this.offerFileRepository = offerFileRepository;
    }

    @Override
    public List<Offer> getUserOfferList(Long userId) {
        LOGGER.debug("Getting user offer list for user: '{}'", userId);
        List<Offer> userOffers = offerFileRepository.getOffersFromJsonFile(OFFERS_FILEPATH);
        return userOffers.stream()
                .filter(offer -> userId.equals(offer.getUser().getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isUserOfferActive(Offer offer) {
        LOGGER.debug("Checking if offer is active: '{}'", offer);
        return offer.getExpiryDate().isAfter(LocalDateTime.now());
    }
}