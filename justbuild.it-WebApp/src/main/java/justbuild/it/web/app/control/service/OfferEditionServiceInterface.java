package justbuild.it.web.app.control.service;

import justbuild.it.web.app.entity.dto.OfferDto;
import org.springframework.stereotype.Service;

@Service
public interface OfferEditionServiceInterface {

    OfferDto getOfferDtoById(Long id);

    void updateOffer(OfferDto editedOfferDto);
}
