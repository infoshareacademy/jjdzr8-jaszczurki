package justbuild.it.web.app.controller;

import justbuild.it.web.app.dto.OfferDTO;
import justbuild.it.web.app.model.Offer;
import justbuild.it.web.app.service.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class WebAppController {

    private final OfferService offerService;

    public WebAppController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/")
    public String goHome(Model model) {
        return "home";
    }

    @GetMapping("/add")
    public String goAdd(Model model) {
        OfferDTO offer = new OfferDTO();
        offer.setOfferID(offerService.getNextOfferId());
        model.addAttribute("offer", offer);
        return "add";
    }

    @PostMapping("/add")
    public String addOffer(@Valid @ModelAttribute("offer") OfferDTO offerDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "add";
        }
        Offer offer = offerDTO.toOffer();
        offerService.addOffer(offer);
        return "redirect:/";
    }
}
