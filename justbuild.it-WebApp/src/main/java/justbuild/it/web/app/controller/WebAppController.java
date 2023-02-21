package justbuild.it.web.app.controller;

import justbuild.it.web.app.dto.OfferDTO;
import justbuild.it.web.app.model.Offer;
import justbuild.it.web.app.service.AddOfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebAppController {

    private final AddOfferService addOfferService;

    public WebAppController(AddOfferService addOfferService) {
        this.addOfferService = addOfferService;
    }

    @GetMapping("/home")
    public String goHome(Model model) {
        return "home";
    }

    @GetMapping("/add")
    public String goAdd(Model model) {
        OfferDTO offer = new OfferDTO();
        offer.setOfferID(addOfferService.getNextOfferId());
        model.addAttribute("offer", offer);
        return "add";
    }

    @PostMapping("/add")
    public String addOffer(@ModelAttribute("offer") OfferDTO offerDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "add";
        }
        Offer offer = offerDTO.toOffer();
        addOfferService.addOffer(offer);
        return "redirect:/home";
    }
}
