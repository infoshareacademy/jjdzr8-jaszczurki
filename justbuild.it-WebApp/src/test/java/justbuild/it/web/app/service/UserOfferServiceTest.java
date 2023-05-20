package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.entity.User;
import justbuild.it.web.app.repository.OfferFileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static justbuild.it.web.app.entity.constants.AppConstants.OFFERS_FILEPATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class UserOfferServiceTest {

    @Mock
    private OfferFileRepository offerFileRepository;

    private UserOfferService userOfferService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userOfferService = new UserOfferService(offerFileRepository);
    }

    @Test
    void testGetUserOfferList() {
        // Given
        Long userId = 1L;
        User user1 = new User();
        user1.setUserId(userId);
        Offer offer1 = new Offer();
        offer1.setUser(user1);

        User user2 = new User();
        user2.setUserId(2L);
        Offer offer2 = new Offer();
        offer2.setUser(user2);

        List<Offer> offers = Arrays.asList(offer1, offer2);

        when(offerFileRepository.getOffersFromJsonFile(OFFERS_FILEPATH)).thenReturn(offers);

        // When
        List<Offer> userOffers = userOfferService.getUserOfferList(userId);

        // Then
        assertEquals(1, userOffers.size());
        assertEquals(userId, userOffers.get(0).getUser().getUserId());
    }

    @Test
    void testIsUserOfferActive() {
        // Given
        Offer activeOffer = new Offer();
        activeOffer.setExpiryDate(LocalDateTime.now().plusDays(1));
        Offer expiredOffer = new Offer();
        expiredOffer.setExpiryDate(LocalDateTime.now().minusDays(1));

        // When
        boolean isActive = userOfferService.isUserOfferActive(activeOffer);
        boolean isExpired = userOfferService.isUserOfferActive(expiredOffer);

        // Then
        assertTrue(isActive);
        assertFalse(isExpired);
    }
}
