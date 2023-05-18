package justbuild.it.web.app.service;

import justbuild.it.web.app.control.repository.OfferFileRepository;
import justbuild.it.web.app.control.repository.OfferRepository;
import justbuild.it.web.app.control.service.OfferCreationService;
import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.entity.User;
import justbuild.it.web.app.entity.enums.ServiceCategoryEnum;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OfferCreationServiceTest {

    private final static String TEST_FILEPATH = "offers.json";
    private final static LocalDateTime NOW = LocalDateTime.now();

    private OfferFileRepository offerFileRepositoryMock;
    private OfferCreationService offerCreationService;
    private OfferRepository offerRepository;
    private static Offer offer;

    @BeforeAll
    static void setUpTest() {
        offer = new Offer(1L, ServiceCategoryEnum.CONSTRUCTION, "some content", "Warsaw", new User("", "", "Company Name", "example@example.com", "111 111 111"), NOW);
    }

    @AfterAll
    static void closeTest() {
        offer = null;
    }

    @BeforeEach
    void setUp() {
        offerFileRepositoryMock = mock(OfferFileRepository.class);
        offerCreationService = new OfferCreationService(offerFileRepositoryMock, offerRepository);
    }

    @Test
    void testIfOfferIsCorrectlyAdded() {
        // given
        List<Offer> existingOffers = new ArrayList<>(Collections.emptyList());
        when(offerFileRepositoryMock.getOffersFromJsonFile(TEST_FILEPATH)).thenReturn(existingOffers);

        // when
        offerCreationService.addOffer(offer);

        // then
        ArgumentCaptor<List<Offer>> listCaptor = ArgumentCaptor.forClass(List.class);
        verify(offerFileRepositoryMock).saveOffersToJsonFile(listCaptor.capture(), eq(TEST_FILEPATH));
        List<Offer> savedOffers = listCaptor.getValue();

        assertThat(savedOffers)
                .hasSize(1)
                .contains(offer);
    }

    @Test
    void testIfOfferIsCorrectlyAddedWhenFileNotExist() {
        // given
        when(offerFileRepositoryMock.getOffersFromJsonFile(TEST_FILEPATH)).thenReturn(null);

        // when
        offerCreationService.addOffer(offer);

        // then
        ArgumentCaptor<List<Offer>> listCaptor = ArgumentCaptor.forClass(List.class);
        verify(offerFileRepositoryMock).saveOffersToJsonFile(listCaptor.capture(), eq(TEST_FILEPATH));
        List<Offer> savedOffers = listCaptor.getValue();

        assertThat(savedOffers)
                .hasSize(1)
                .contains(offer);
    }

    @Test
    void testIfNextOfferIdIsCorrectlyReturned() {
        // given
        List<Offer> existingOffers = new ArrayList<>(List.of(offer));
        when(offerFileRepositoryMock.getOffersFromJsonFile(TEST_FILEPATH)).thenReturn(existingOffers);

        // when
        Long nextOfferId = offerCreationService.getNextOfferId();

        // then
        assertThat(nextOfferId).isEqualTo(2L);
    }

    @Test
    void testIfNextOfferIdIsCorrectlyReturnedWhenListIsEmpty() {
        // given
        List<Offer> notExistingOffers = new ArrayList<>(Collections.emptyList());
        when(offerFileRepositoryMock.getOffersFromJsonFile(TEST_FILEPATH)).thenReturn(notExistingOffers);

        // when
        Long nextOfferIdWhenEmptyList = offerCreationService.getNextOfferId();

        // then
        assertThat(nextOfferIdWhenEmptyList).isEqualTo(1L);
    }
}