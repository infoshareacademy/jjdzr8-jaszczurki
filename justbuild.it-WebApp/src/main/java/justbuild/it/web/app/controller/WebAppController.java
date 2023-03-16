package justbuild.it.web.app.controller;

import justbuild.it.web.app.dto.OfferDto;
import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.mapper.OfferMapper;
import justbuild.it.web.app.service.OfferEditService;
import justbuild.it.web.app.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.util.List;

@Controller
public class WebAppController {

    private final OfferService offerService;
    private final OfferMapper mapper;
    private final OfferEditService offerEditService;

    public WebAppController(OfferService offerService, OfferMapper mapper, OfferEditService offerEditService) {
        this.offerService = offerService;
        this.mapper = mapper;
        this.offerEditService = offerEditService;
    }

    @GetMapping("/")
    public String goHome(Model model) {
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
        offerService.addOffer(offer);
        return "redirect:/";
    }

    @GetMapping("/searchOffer")
    public String goSearch(String searchValue, String category, Model model) {
        List<OfferDto> filteredOfferDtoList;
        if (category != null && !category.isEmpty()){
            filteredOfferDtoList = offerService.provideNewFilteredByCategoryOfferDtoList(category);
        } else {
            filteredOfferDtoList = offerService.provideNewFilteredOfferDtoList(searchValue);
        }
        model.addAttribute("filteredOfferDtoList", filteredOfferDtoList);
        return "searchOffer";
    }

    @GetMapping("/editOffer/{id}")
    public String goEdit(@PathVariable Long id, Model model) {
        model.addAttribute("offer", offerEditService.getOfferDtoById(id));
        return "editOffer";
    }

    @PostMapping("/editOffer")
    public String editOffer(@Valid @ModelAttribute("offer") OfferDto offerDto, BindingResult result) {
        if (result.hasErrors()) {
            return "editOffer";
        }
        offerEditService.updateOffer(offerDto);
        return "redirect:/";
    }
}