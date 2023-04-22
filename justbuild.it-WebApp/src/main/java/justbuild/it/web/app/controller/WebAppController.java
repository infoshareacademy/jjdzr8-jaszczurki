package justbuild.it.web.app.controller;

import justbuild.it.web.app.dto.OfferDto;
import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.mapper.OfferMapper;
import justbuild.it.web.app.service.OfferService;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class WebAppController {

    private final OfferService offerService;
    private final OfferMapper mapper;

    public WebAppController(OfferService offerService, OfferMapper mapper) {
        this.offerService = offerService;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public String goHome(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "9") int size) {
        List<OfferDto> allOfferDtoList = offerService.provideAllDtoList();
        Page<OfferDto> offerDtoPage = offerService.providePagination(PageRequest.of(page - 1, size), allOfferDtoList);
        model.addAttribute("offerDtoPage", offerDtoPage);
        model.addAttribute("pageNumbers", offerService.calculatePageNumbers(offerDtoPage));
        return "home";
    }

    @GetMapping("/addOffer")
    public String goAdd(Model model) {
        model.addAttribute("offer", offerService.provideNewOffer());
        return "addOffer";
    }

    @PostMapping("/addOffer")
    public String addOffer(@Valid @ModelAttribute("offer") OfferDto offerDto, BindingResult result) {
        if (result.hasErrors()) {
            return "addOffer";
        }
        Offer offer = mapper.fromDto(offerDto);
        offerService.setIdToOffer(offer);
        offerService.addOffer(offer);
        return "redirect:/";
    }

    @GetMapping("/searchOffer")
    public String goSearch(@RequestParam(required = false) String searchValue, @RequestParam(required = false) String category,
                           @RequestParam(defaultValue = "1") int pageList, @RequestParam(defaultValue = "8") int sizeList,
                           HttpSession session, Model model) {
        if (searchValue == null) {
            searchValue = "";
        }
        if (category == null) {
            category = "";
        }
        List<OfferDto> filteredOfferDtoList;
        if (pageList == 1 || session.getAttribute("filteredOfferDtoList") == null) {
            filteredOfferDtoList = offerService.provideOfferDtoList(searchValue, category);
            session.setAttribute("filteredOfferDtoList", filteredOfferDtoList);
        } else {
            filteredOfferDtoList = (List<OfferDto>) session.getAttribute("filteredOfferDtoList");
        }
        Page<OfferDto> offerDtoListPage = offerService.providePagination(PageRequest.of(pageList - 1, sizeList), filteredOfferDtoList);
        model.addAttribute("filteredOfferDtoList", offerDtoListPage);
        model.addAttribute("pagesDtoList", offerService.calculatePageNumbers(offerDtoListPage));
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("category", category);
        return "searchOffer";
    }

    @GetMapping("/editOffer/{id}")
    public String goEdit(@PathVariable Long id, Model model) {
        model.addAttribute("offer", offerService.getOfferDtoById(id));
        return "editOffer";
    }

    @PostMapping("/editOffer")
    public String editOffer(@Valid @ModelAttribute("offer") OfferDto offerDto, BindingResult result) {
        if (result.hasErrors()) {
            return "editOffer";
        }
        offerService.updateOffer(offerDto);
        return "redirect:/";
    }
}