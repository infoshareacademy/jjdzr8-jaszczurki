package justbuild.it.web.app.mapper;

import justbuild.it.web.app.dto.OfferDto;
import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OfferMapper {

    public OfferDto toDto(Offer offer) {
        OfferDto dto = new OfferDto();
        dto.setDtoOfferId(offer.getOfferId());
        dto.setServiceCategory(offer.getServiceCategory());
        dto.setOfferContent(offer.getOfferContent());
        dto.setCity(offer.getCity());
        dto.setDateTime(offer.getDate());
        dto.setUserId(offer.getUser().getUserId());
        dto.setUserFirstName(offer.getUser().getFirstName());
        dto.setUserLastName(offer.getUser().getLastName());
        dto.setUserCompanyName(offer.getUser().getCompany());
        dto.setUserEmailAddress(offer.getUser().getEmailAddress());
        dto.setUserTelephoneNumber(offer.getUser().getTelephoneNumber());
        dto.setExpiryDate(offer.getExpiryDate());
        return dto;
    }

    public Offer fromDto(OfferDto dto) {
        Offer offer = new Offer();
        offer.setOfferId(dto.getDtoOfferId());
        offer.setServiceCategory(dto.getServiceCategory());
        offer.setOfferContent(dto.getOfferContent());
        offer.setCity(dto.getCity());
        offer.setDate(dto.getDateTime());
        offer.setExpiryDate(dto.getExpiryDate());
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setFirstName(dto.getUserFirstName());
        user.setLastName(dto.getUserLastName());
        user.setCompany(dto.getUserCompanyName());
        user.setEmailAddress(dto.getUserEmailAddress());
        user.setTelephoneNumber(dto.getUserTelephoneNumber());
        offer.setUser(user);
        return offer;
    }

    public List<OfferDto> toDtoList(List<Offer> offerList) {
        return offerList.stream()
                .map(offer -> new OfferDto(offer.getOfferId(), offer.getServiceCategory(), offer.getOfferContent(),
                        offer.getCity(), offer.getUser().getUserId(), offer.getUser().getFirstName(), offer.getUser().getLastName(),
                        offer.getUser().getCompany(), offer.getUser().getEmailAddress(),
                        offer.getUser().getTelephoneNumber(), offer.getDate(), offer.getExpiryDate()))
                .collect(Collectors.toList());
    }
}