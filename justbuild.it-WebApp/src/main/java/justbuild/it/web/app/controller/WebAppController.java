package justbuild.it.web.app.controller;

import justbuild.it.web.app.dto.OfferDto;
import justbuild.it.web.app.dto.ProlongRequest;
import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.entity.User;
import justbuild.it.web.app.mapper.OfferMapper;
import justbuild.it.web.app.service.OfferService;
import justbuild.it.web.app.service.UserOfferService;
import justbuild.it.web.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class WebAppController {

    private final OfferService offerService;
    private final UserService userService;
    private final UserOfferService userOfferService;
    private final OfferMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(WebAppController.class);

    public WebAppController(OfferService offerService, UserService userService, UserOfferService userOfferService, OfferMapper mapper) {
        this.offerService = offerService;
        this.userService = userService;
        this.userOfferService = userOfferService;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public String goHome(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "9") int size) {
        List<OfferDto> allOfferDtoList = offerService.provideAllDtoList();
        Page<OfferDto> offerDtoPage = offerService.providePagination(PageRequest.of(page - 1, size), allOfferDtoList);
        List<Integer> pageNumbers = offerService.calculatePageNumbers(offerDtoPage);
        model.addAttribute("offerDtoPage", offerDtoPage);
        model.addAttribute("pageNumbers", pageNumbers);
        LOGGER.info("Returning home page with '{}' pages", pageNumbers.size());
        return "home";
    }

    @PostMapping("/prolong-ad")
    public void prolongAd(@RequestBody ProlongRequest request) {
        offerService.prolongOffer(request.getId(), request.getDays());
    }

    @GetMapping("/user/addOffer")
    public String goAdd(Model model) {
        model.addAttribute("offer", offerService.provideNewOffer());
        LOGGER.info("Opening addOffer page");
        return "addOffer";
    }

    @PostMapping("/user/addOffer")
    public String addOffer(@Valid @ModelAttribute("offer") OfferDto offerDto, BindingResult result) {
        if (result.hasErrors()) {
            return "addOffer";
        }

        Offer offer = mapper.fromDto(offerDto);
        offerService.setIdToOffer(offer);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Long userId = userService.getUserIdByUsername(username);
            User user = offer.getUser();
            user.setUserId(userId);
            offer.setUser(user);
        }
        offerService.addOffer(offer);
        LOGGER.info("New offer added with ID: {}", offer.getOfferId());
        return "redirect:/";
    }

    @GetMapping("/searchOffer")
    public String goSearch(@RequestParam(required = false) String searchValue, @RequestParam(required = false) String category,
                           @RequestParam(defaultValue = "1") int pageList, @RequestParam(defaultValue = "8") int sizeList,
                           HttpSession session, Model model) {
        LOGGER.info("Searching for offers with searchValue: '{}' and category: '{}'", searchValue, category);
        List<OfferDto> filteredOfferDtoList = offerService.provideFilteredList(searchValue, category, pageList, session);
        Page<OfferDto> offerDtoListPage = offerService.providePagination(PageRequest.of(pageList - 1, sizeList), filteredOfferDtoList);
        model.addAttribute("filteredOfferDtoList", offerDtoListPage);
        model.addAttribute("pagesDtoList", offerService.calculatePageNumbers(offerDtoListPage));
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("category", category);
        LOGGER.info("Returning searchOffer page with '{}' offers found", filteredOfferDtoList.size());
        return "searchOffer";
    }

    @GetMapping("editOffer/{id}")
    public String goEdit(@PathVariable Long id, Model model) {
        OfferDto offerDto = offerService.getOfferDtoById(id);
        boolean prolongable = offerService.checkOfferProlongability(offerDto);
        boolean expired = LocalDateTime.now().isAfter(offerDto.getExpiryDate());
        model.addAttribute("offer", offerDto);
        model.addAttribute("prolongable", prolongable);
        model.addAttribute("expired", expired);
        LOGGER.info("Opening editOffer page for offer with ID: {}", id);
        return "editOffer";
    }

    @PostMapping("editOffer")
    public String editOffer(@Valid @ModelAttribute("offer") OfferDto offerDto, BindingResult result) {
        if (result.hasErrors()) {
            return "editOffer";
        }
        offerService.updateOffer(offerDto);
        LOGGER.info("Updated offer with ID: {}", offerDto.getDtoOfferId());

        return "redirect:/";
    }

    @GetMapping("myOffers")
    public String showUserOffers(Model model) {
        LOGGER.info("Searching for user offers");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            String username = authentication.getName();
            Long userId = userService.getUserIdByUsername(username);

            List<OfferDto> userActiveOffers = offerService.provideActiveUserOffers(userId);
            List<OfferDto> userInactiveOffers = offerService.provideInactiveUserOffers(userId);

            model.addAttribute("activeFilteredOfferDtoList", userActiveOffers);
            model.addAttribute("inactiveFilteredOfferDtoList", userInactiveOffers);

            LOGGER.info("Returning user Offers list page with '{}' active offers found", userActiveOffers.size());
            LOGGER.info("Returning user Offers list page with '{}' inactive offers found", userInactiveOffers.size());
            return "myOffers";
        } else {
            return "loginForm";
        }
    }

    @GetMapping("/terminateOffer/{id}")
    public ResponseEntity<Offer> terminateOffer(Long id) {
        offerService.terminateOffer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}