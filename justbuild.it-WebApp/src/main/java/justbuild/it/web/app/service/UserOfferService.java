package justbuild.it.web.app.service;

import justbuild.it.web.app.dto.OfferDto;
import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.repository.OfferFileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public List<Offer> getUserOfferList(Long userId) {
        List<Offer> userOffers = offerFileRepository.getOffersFromJsonFile(OFFERS_FILEPATH);
        return userOffers.stream()
                .filter(offer -> userId.equals(offer.getUser().getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isUserOfferActive(Offer offer) {
        return offer.getExpiryDate().isAfter(LocalDateTime.now());
    }
    @Override
    public boolean isLoggedUserOwnerOfOffer(OfferDto offerDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            String username = authentication.getName();
            Long userId = userService.getUserIdByUsername(username);
            System.out.println("UserId: " + userId + " " + authentication);
            System.out.println("OfferUserId : " + offerDto.getUserId());
            return userId.equals(offerDto.getUserId());
        } else {
            return false;
        }
    }
}