//package justbuild.it.web.app.service;
//
//import justbuild.it.web.app.entity.Offer;
//import justbuild.it.web.app.entity.User;
//import justbuild.it.web.app.entity.enums.ServiceCategoryEnum;
//import justbuild.it.web.app.repository.OfferFileRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Stream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertAll;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertIterableEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class OfferSearchingServiceTest {
//    private OfferFileRepository offerFileRepositoryMock;
//    private OfferSearchingService offerSearchingService;
//
//    private static final Offer OFFER_1 = new Offer(1L, ServiceCategoryEnum.CONSTRUCTION, "Przykładowa oferta budowy", "Gdańsk", new User(id, "Jan", "Kowalski", "KowalBud", "kowal@example.com", "111222333"), LocalDateTime.now().minusHours(1));
//    private static final Offer OFFER_2 = new Offer(2L, ServiceCategoryEnum.GARDEN, "Projektowanie ogrodu", "Szczecin", new User(id, "Filip", "Nowak", "NowakGarden", "nowak@example.com", "123456789"), LocalDateTime.now().minusDays(1));
//    private static final Offer OFFER_3 = new Offer(3L, ServiceCategoryEnum.FINISHING_WORKS, "Prace wykończeniowe", "Warszawa", new User(id, "Roman", "Romanowski", "RomanBud", "romanBud@example.com", "987654321"), LocalDateTime.now().minusDays(2));
//    private static final Offer OFFER_4 = new Offer(4L, ServiceCategoryEnum.EARTH_WORKS, "Prace ziemne", "Warszawa", new User(id, "Tomasz", "Filipiak", "Filipiak&Son", "filipiak@example.com", "435657345"), LocalDateTime.now().minusDays(8));
//    private static final Offer OFFER_5 = new Offer(5L, ServiceCategoryEnum.ELECTRICITY, "Instalacje elektryczne", "Radom", new User(id, "Tadeusz", "Wolski", "InstallWol", "install@example.com", "435657345"), LocalDateTime.now().minusDays(1));
//    private static final Offer OFFER_6 = new Offer(6L, ServiceCategoryEnum.INSTALLATION, "Kanalizacja", "Kraków", new User(id, "Andrzej", "Redlin", "RedLine", "redline@example.com", "567897546"), LocalDateTime.now().minusDays(3));
//    private static final Offer OFFER_7 = new Offer(7L, ServiceCategoryEnum.GARDEN, "Aranżacja ogrodu", "Nowy Sącz", new User(id, "Eustachy", "Konieczko", "Gardens", "gardens@example.com", "1232343212"), LocalDateTime.now().minusDays(6));
//
//    private static final List<Offer> GIVEN_TEST_LIST = List.of(OFFER_1, OFFER_2, OFFER_3, OFFER_4, OFFER_5, OFFER_6, OFFER_7);
//
//    @BeforeEach
//    public void setUpTest() {
//        offerFileRepositoryMock = mock(OfferFileRepository.class);
//        offerSearchingService = new OfferSearchingService(offerFileRepositoryMock);
//    }
//
//    @Test
//    void getOffersList_shouldReturnOfferList() {
//        //Given
//        List<Offer> expectedOffersList = GIVEN_TEST_LIST;
//        when(offerFileRepositoryMock.getOffersFromJsonFile(anyString())).thenReturn(GIVEN_TEST_LIST);
//        // When
//        List<Offer> testOffersList = offerSearchingService.getOffersList();
//        // Then
//        assertAll("Offer List",
//                () -> assertNotNull(testOffersList),
//                () -> assertEquals(expectedOffersList.size(), testOffersList.size()),
//                () -> assertIterableEquals(expectedOffersList, testOffersList)
//        );
//    }
//
//    @Test
//    void getOffersList_shouldReturnEmptyList() {
//        //Given
//        when(offerFileRepositoryMock.getOffersFromJsonFile(anyString())).thenReturn(Collections.emptyList());
//        // When
//        List<Offer> testOffersList = offerSearchingService.getOffersList();
//        // Then
//        assertThat(testOffersList)
//                .isEmpty();
//    }
//
//    @ParameterizedTest
//    @MethodSource("getSearchingValueAndExpectedList")
//    void getOffersListFilteredBySearchValue_shouldReturnListFilteredBySearchValue(String searchingValue, List<Offer> expectedOffersList) {
//        //Given
//        when(offerFileRepositoryMock.getOffersFromJsonFile(anyString())).thenReturn(GIVEN_TEST_LIST);
//        //When
//        List<Offer> testOfferList = offerSearchingService.getOffersListFilteredBySearchValue(searchingValue);
//        //Then
//        assertAll("Offer List",
//                () -> assertNotNull(testOfferList),
//                () -> assertEquals(expectedOffersList.size(), testOfferList.size()),
//                () -> assertIterableEquals(expectedOffersList, testOfferList)
//        );
//    }
//
//    @Test
//    void getOffersListFilteredBySearchValue_shouldReturnEmptyList() {
//        //Given
//        when(offerFileRepositoryMock.getOffersFromJsonFile(anyString())).thenReturn(Collections.emptyList());
//        // When
//        List<Offer> testOffersList = offerSearchingService.getOffersListFilteredBySearchValue(anyString());
//        // Then
//        assertThat(testOffersList)
//                .isEmpty();
//    }
//
//    @ParameterizedTest
//    @MethodSource("getCategoryAndExpectedOffersList")
//    void getOffersListFilteredByCategory_shouldReturnListFilteredByCategory(String category, List<Offer> expectedOffersList) {
//        //Given
//        when(offerFileRepositoryMock.getOffersFromJsonFile(anyString())).thenReturn(GIVEN_TEST_LIST);
//        // When
//        List<Offer> testOffersList = offerSearchingService.getOffersListFilteredByCategory(category);
//        // Then
//        assertAll("Offer List",
//                () -> assertNotNull(testOffersList),
//                () -> assertEquals(expectedOffersList.size(), testOffersList.size()),
//                () -> assertIterableEquals(expectedOffersList, testOffersList)
//        );
//    }
//
//    @Test
//    void getOffersListFilteredByCategory_shouldReturnEmptyList() {
//        //Given
//        when(offerFileRepositoryMock.getOffersFromJsonFile(anyString())).thenReturn(Collections.emptyList());
//        // When
//        List<Offer> testOffersList = offerSearchingService.getOffersListFilteredByCategory(anyString());
//        // Then
//        assertThat(testOffersList)
//                .isEmpty();
//    }
//
//    static Stream<Arguments> getSearchingValueAndExpectedList() {
//        return Stream.of(
//                Arguments.of("proje", List.of(OFFER_2)),
//                Arguments.of("warSza", List.of(OFFER_3, OFFER_4)),
//                Arguments.of("REDLIN", List.of(OFFER_6)),
//                Arguments.of("1", List.of(OFFER_1)),
//                Arguments.of("elektryka", List.of(OFFER_5)),
//                Arguments.of("", List.of(OFFER_1, OFFER_5, OFFER_2, OFFER_3, OFFER_6, OFFER_7, OFFER_4))
//        );
//    }
//
//    static Stream<Arguments> getCategoryAndExpectedOffersList() {
//        return Stream.of(
//                Arguments.of("Budowa", List.of(OFFER_1)),
//                Arguments.of("Remont", List.of(OFFER_3)),
//                Arguments.of("Instalacje", List.of(OFFER_6)),
//                Arguments.of("Elektryka", List.of(OFFER_5)),
//                Arguments.of("Roboty ziemne", List.of(OFFER_4)),
//                Arguments.of("Ogród", List.of(OFFER_2, OFFER_7))
//        );
//    }
//}