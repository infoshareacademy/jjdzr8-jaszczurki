package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.entity.User;
import justbuild.it.web.app.repository.OfferFileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static justbuild.it.web.app.entity.constants.AppConstants.OFFERS_FILEPATH;

@Service
public class UserOfferService implements UserOffersInterface {

    private final OfferFileRepository offerFileRepository;
    private final UserService userService;

    public UserOfferService(OfferFileRepository offerFileRepository, UserService userService) {
        this.offerFileRepository = offerFileRepository;
        this.userService = userService;
    }

    @Override
    public List<Offer> getUserOfferList(User user) {
        List<Offer> userOffers = offerFileRepository.getOffersFromJsonFile(OFFERS_FILEPATH);
        return userOffers.stream()
                .filter(offer -> offer.getUser().getUserId().equals(user.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isUserOfferActive(Offer offer) {
        return offer.getExpiryDate().isAfter(LocalDateTime.now());
    }
}