package justbuild.it.web.app.service;

import justbuild.it.web.app.dto.OfferDto;
import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.mapper.OfferMapper;
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

    public OfferService(OfferCreationService offerCreationService, OfferSearchingService offerSearchingService, OfferEditionService offerEditionService) {
        this.offerCreationService = offerCreationService;
        this.offerSearchingService = offerSearchingService;
        this.offerEditionService = offerEditionService;
    }

    public void addOffer(Offer offer) {
        offerCreationService.addOffer(offer);
    }

    public OfferDto provideNewOffer() {
        OfferDto offer = new OfferDto();
        offer.setDtoOfferId(offerCreationService.getNextOfferId());
        return offer;
    }

    public List<OfferDto> provideNewFilteredOfferDtoList(String searchValue) {
        OfferMapper offerMapper = new OfferMapper();
        List<OfferDto> filteredOfferDtoList;
        filteredOfferDtoList = offerMapper.toDtoList(offerSearchingService.getOffersListFilteredBySearchValue(searchValue));
        return filteredOfferDtoList;
    }

    public List<OfferDto> provideNewFilteredByCategoryOfferDtoList(String category) {
        OfferMapper offerMapper = new OfferMapper();
        List<OfferDto> filteredByCategoryOfferDtoList;
        filteredByCategoryOfferDtoList = offerMapper.toDtoList(offerSearchingService.getOffersListFilteredByCategory(category));
        return filteredByCategoryOfferDtoList;
    }

    public List<OfferDto> provideAllDtoList() {
        OfferMapper offerMapper = new OfferMapper();
        List<OfferDto> allOfferDtoList;
        allOfferDtoList = offerMapper.toDtoList(offerSearchingService.getOffersList());
        return allOfferDtoList;
    }

    public OfferDto getOfferDtoById(Long id) {
        return offerEditionService.getOfferDtoById(id);
    }

    public void updateOffer(OfferDto editedOfferDto) {
        offerEditionService.updateOffer(editedOfferDto);
        
    public Page<OfferDto> findPaginated(Pageable pageable, List<OfferDto> allOfferDtoList) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<OfferDto> offerList;

        if (allOfferDtoList.size() < startItem) {
            offerList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, allOfferDtoList.size());
            offerList = allOfferDtoList.subList(startItem, toIndex);
        }

        return new PageImpl<OfferDto>(offerList, PageRequest.of(currentPage, pageSize), allOfferDtoList.size());

    }
}