package justbuild.it.web.app.service;

import justbuild.it.web.app.dto.OfferDto;
import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.mapper.OfferMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

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
        return new OfferDto();
    }

    public void setIdToOffer(Offer offer) {
        offer.setOfferId(offerCreationService.getNextOfferId());
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
        allOfferDtoList.sort(Comparator.comparing(OfferDto::getDateTime).reversed());
        return allOfferDtoList;
    }

    public List<OfferDto> provideOfferDtoList(String searchValue, String category) {
        List<OfferDto> filteredOfferDtoList;
        if (category != null && !category.isEmpty()){
            filteredOfferDtoList = provideNewFilteredByCategoryOfferDtoList(category);
        } else {
            filteredOfferDtoList = provideNewFilteredOfferDtoList(searchValue);
        }
        return filteredOfferDtoList;
    }

    public OfferDto getOfferDtoById(Long id) {
        OfferDto offerDtoById = offerEditionService.getOfferDtoById(id);
        offerDtoById.setDateTime(LocalDateTime.now());
        return offerDtoById;
    }

    public void updateOffer(OfferDto editedOfferDto) {
        offerEditionService.updateOffer(editedOfferDto);
    }

    public List<OfferDto> provideFilteredList(String searchValue, String category, int pageList, HttpSession session) {
        if (searchValue == null) {
            searchValue = "";
        }
        if (category == null) {
            category = "";
        }
        List<OfferDto> filteredDtoList;
        if (pageList == 1 || session.getAttribute("filteredOfferDtoList") == null) {
            filteredDtoList = provideOfferDtoList(searchValue, category);
            session.setAttribute("filteredOfferDtoList", filteredDtoList);
        } else {
            filteredDtoList = (List<OfferDto>) session.getAttribute("filteredOfferDtoList");
        }

        return filteredDtoList;
    }

    public Page<OfferDto> providePagination(Pageable pageable, List<OfferDto> allResources) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<OfferDto> offerDtos;

        if (allResources.isEmpty() || allResources.size() < startItem) {
            offerDtos = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, allResources.size());
            offerDtos = allResources.subList(startItem, toIndex);
        }

        return new PageImpl<>(offerDtos, pageable, allResources.size());
    }

    public List<Integer> calculatePageNumbers(Page<?> page) {
        int totalPages = page.getTotalPages();
        if (totalPages > 0) {
            int maxVisiblePages = 5;
            int startPage = Math.max(1, page.getNumber() - (maxVisiblePages / 2));
            int endPage = Math.min(totalPages, startPage + maxVisiblePages - 1);
            return IntStream.rangeClosed(startPage, endPage)
                    .boxed()
                    .toList();
        } else {
            return Collections.emptyList();
        }
    }
}