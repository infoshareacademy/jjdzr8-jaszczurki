package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.entity.User;
import justbuild.it.web.app.entity.enums.ServiceCategoryEnum;
import justbuild.it.web.app.repository.OfferFileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OfferCreationServiceTest {

    private OfferFileRepository offerFileRepositoryMock;
    private OfferCreationService offerCreationService;
    private Offer offer;
    private final static String TEST_FILEPATH = "offers.json";


    @BeforeEach
    void SetUpTest() {
        offerFileRepositoryMock = mock(OfferFileRepository.class);
        offerCreationService = new OfferCreationService(offerFileRepositoryMock);

        offer = new Offer(1L, ServiceCategoryEnum.CONSTRUCTION, "some content", "Warsaw", new User("", "", "Company Name", "example@example.com", "111 111 111"), LocalDateTime.now());
    }

    @AfterEach
    void closeTest() {
        offerFileRepositoryMock = null;
        offerCreationService = null;
        offer = null;
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

        assertTrue(savedOffers.contains(offer));
        assertEquals(1, savedOffers.size());
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

        assertTrue(savedOffers.contains(offer));
        assertEquals(1, savedOffers.size());
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