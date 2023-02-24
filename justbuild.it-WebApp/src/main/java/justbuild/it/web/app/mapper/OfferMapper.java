package justbuild.it.web.app.mapper;

import justbuild.it.web.app.dto.OfferDTO;
import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.entity.User;
import org.springframework.stereotype.Component;

@Component
public class OfferMapper {

    public OfferDTO toDto(Offer offer) {
        OfferDTO dto = new OfferDTO();
        dto.setOfferID(offer.getOfferID());
        dto.setServiceCategory(offer.getServiceCategory());
        dto.setOfferContent(offer.getOfferContent());
        dto.setCity(offer.getCity());
        dto.setDateTime(offer.getDate());
        dto.setUserFirstName(offer.getUser().getFirstName());
        dto.setUserLastName(offer.getUser().getLastName());
        dto.setUserCompanyName(offer.getUser().getCompany());
        dto.setUserEmailAddress(offer.getUser().getEmailAddress());
        dto.setUserTelephoneNumber(offer.getUser().getTelephoneNumber());
        return dto;
    }

    public Offer fromDto(OfferDTO dto) {
        Offer offer = new Offer();
        offer.setOfferID(dto.getOfferID());
        offer.setServiceCategory(dto.getServiceCategory());
        offer.setOfferContent(dto.getOfferContent());
        offer.setCity(dto.getCity());
        offer.setDate(dto.getDateTime());
        User user = new User();
        user.setFirstName(dto.getUserFirstName());
        user.setLastName(dto.getUserLastName());
        user.setCompany(dto.getUserCompanyName());
        user.setEmailAddress(dto.getUserEmailAddress());
        user.setTelephoneNumber(dto.getUserTelephoneNumber());
        offer.setUser(user);
        return offer;
    }
}
