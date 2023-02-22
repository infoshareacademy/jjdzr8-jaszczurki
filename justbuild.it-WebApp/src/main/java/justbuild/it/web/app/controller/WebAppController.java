package justbuild.it.web.app.controller;

import justbuild.it.web.app.dto.OfferDTO;
import justbuild.it.web.app.model.Offer;
import justbuild.it.web.app.service.OfferCreationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class WebAppController {

    private final OfferCreationService offerCreationService;

    public WebAppController(OfferCreationService offerCreationService) {
        this.offerCreationService = offerCreationService;
    }

    @GetMapping("/home")
    public String goHome(Model model) {
        return "home";
    }

    @GetMapping("/add")
    public String goAdd(Model model) {
        OfferDTO offer = new OfferDTO();
        offer.setOfferID(offerCreationService.getNextOfferId());
        model.addAttribute("offer", offer);
        return "add";
    }

    @PostMapping("/add")
    public String addOffer(@Valid @ModelAttribute("offer") OfferDTO offerDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "add";
        }
        Offer offer = offerDTO.toOffer();
        offerCreationService.addOffer(offer);
        return "redirect:/home";
    }
}
