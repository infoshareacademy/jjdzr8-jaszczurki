package justbuild.it.web.app.controller;

import justbuild.it.web.app.dto.OfferDto;
import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.mapper.OfferMapper;
import justbuild.it.web.app.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class WebAppController {

    private final OfferService offerService;
    private final OfferMapper mapper;
    private final Logger LOGGER;

    public WebAppController(OfferService offerService, OfferMapper mapper) {
        this.offerService = offerService;
        this.mapper = mapper;
        this.LOGGER = LoggerFactory.getLogger(WebAppController.class);
    }

    @GetMapping("/")
    public String goHome(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(9);

        List<OfferDto> allOfferDtoList = offerService.provideAllDtoList();
        Page<OfferDto> offerDtoPage = offerService.findPaginated(PageRequest.of(currentPage - 1, pageSize), allOfferDtoList);

        model.addAttribute("offerDtoPage", offerDtoPage);

        int totalPages = offerDtoPage.getTotalPages();
        if (totalPages > 0) {
            int maxVisiblePages = 5;
            int startPage = Math.max(1, currentPage - (maxVisiblePages / 2));
            int endPage = Math.min(totalPages, startPage + maxVisiblePages - 1);
            List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        LOGGER.info("Returning home page with {} pages", totalPages);
        return "home";
    }

    @GetMapping("/addOffer")
    public String goAdd(Model model) {
        model.addAttribute("offer", offerService.provideNewOffer());
        LOGGER.info("Opening addOffer page");
        return "addOffer";
    }

    @PostMapping("/addOffer")
    public String addOffer(@Valid @ModelAttribute("offer") OfferDto offerDto, BindingResult result) {
        if (result.hasErrors()) {
            return "addOffer";
        }
        Offer offer = mapper.fromDto(offerDto);
        offerService.addOffer(offer);
        LOGGER.info("New offer added with ID {}", offer.getOfferId());
        return "redirect:/";
    }

    @GetMapping("/searchOffer")
    public String goSearch(String searchValue, String category, Model model) {
        LOGGER.info("Searching for offers with searchValue: {} and category: {}", searchValue, category);
        List<OfferDto> filteredOfferDtoList;
        if (category != null && !category.isEmpty()){
            filteredOfferDtoList = offerService.provideNewFilteredByCategoryOfferDtoList(category);
        } else {
            filteredOfferDtoList = offerService.provideNewFilteredOfferDtoList(searchValue);
        }
        model.addAttribute("filteredOfferDtoList", filteredOfferDtoList);
        LOGGER.info("Returning searchOffer page with {} offers found", filteredOfferDtoList.size());
        return "searchOffer";
    }

    @GetMapping("/editOffer/{id}")
    public String goEdit(@PathVariable Long id, Model model) {
        model.addAttribute("offer", offerService.getOfferDtoById(id));
        LOGGER.info("Opening editOffer page for offer with id: {}", id);
        return "editOffer";
    }

    @PostMapping("/editOffer")
    public String editOffer(@Valid @ModelAttribute("offer") OfferDto offerDto, BindingResult result) {
        if (result.hasErrors()) {
            return "editOffer";
        }
        offerService.updateOffer(offerDto);
        LOGGER.info("Updated offer with id {}", offerDto.getDtoOfferId());
        return "redirect:/";
    }
}