package justbuild.it.web.app.service;

import justbuild.it.web.app.dto.OfferDto;
import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.mapper.OfferMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class OfferService {

    private final OfferCreationService offerCreationService;
    private final OfferSearchingService offerSearchingService;
    private final OfferEditionService offerEditionService;
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferService.class);

    public OfferService(OfferCreationService offerCreationService, OfferSearchingService offerSearchingService, OfferEditionService offerEditionService) {
        this.offerCreationService = offerCreationService;
        this.offerSearchingService = offerSearchingService;
        this.offerEditionService = offerEditionService;
    }

    public void addOffer(Offer offer) {
        LOGGER.debug("Adding offer: '{}'", offer);
        offerCreationService.addOffer(offer);
    }

    public OfferDto provideNewOffer() {
        LOGGER.debug("Providing new offer DTO");
        OfferDto offer = new OfferDto();
        offer.setDtoOfferId(offerCreationService.getNextOfferId());
        return offer;
    }

    public List<OfferDto> provideNewFilteredOfferDtoList(String searchValue) {
        LOGGER.debug("Providing new filtered offer DTO list for search value: '{}'", searchValue);
        OfferMapper offerMapper = new OfferMapper();
        List<OfferDto> filteredOfferDtoList;
        filteredOfferDtoList = offerMapper.toDtoList(offerSearchingService.getOffersListFilteredBySearchValue(searchValue));
        return filteredOfferDtoList;
    }

    public List<OfferDto> provideNewFilteredByCategoryOfferDtoList(String category) {
        LOGGER.debug("Providing new filtered by category offer DTO list for category: '{}'", category);
        OfferMapper offerMapper = new OfferMapper();
        List<OfferDto> filteredByCategoryOfferDtoList;
        filteredByCategoryOfferDtoList = offerMapper.toDtoList(offerSearchingService.getOffersListFilteredByCategory(category));
        return filteredByCategoryOfferDtoList;
    }

    public List<OfferDto> provideAllDtoList() {
        LOGGER.debug("Providing all offer DTO list");
        OfferMapper offerMapper = new OfferMapper();
        List<OfferDto> allOfferDtoList;
        allOfferDtoList = offerMapper.toDtoList(offerSearchingService.getOffersList());
        return allOfferDtoList;
    }

    public OfferDto getOfferDtoById(Long id) {
        LOGGER.debug("Getting offer DTO by ID: {}", id);
        return offerEditionService.getOfferDtoById(id);
    }

    public void updateOffer(OfferDto editedOfferDto) {
        LOGGER.debug("Updating offer DTO: '{}'", editedOfferDto);
        offerEditionService.updateOffer(editedOfferDto);
    }
        
    public Page<OfferDto> findPaginated(Pageable pageable, List<OfferDto> allOfferDtoList) {
        LOGGER.debug("Finding paginated offer DTO list with page: '{}' and size: '{}'", pageable.getPageNumber() + 1, pageable.getPageSize());
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<OfferDto> offerList;

        LOGGER.debug("Size of allOfferDtoList: '{}'", allOfferDtoList.size());

        if (allOfferDtoList.size() < startItem) {
            offerList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, allOfferDtoList.size());
            offerList = allOfferDtoList.subList(startItem, toIndex);
        }

        LOGGER.debug("Size of offerList: '{}'", offerList.size());

        return new PageImpl<OfferDto>(offerList, PageRequest.of(currentPage, pageSize), allOfferDtoList.size());
    }
}