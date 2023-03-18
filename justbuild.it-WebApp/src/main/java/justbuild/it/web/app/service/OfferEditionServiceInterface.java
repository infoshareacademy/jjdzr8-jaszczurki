package justbuild.it.web.app.service;

import justbuild.it.web.app.dto.OfferDto;
import org.springframework.stereotype.Service;

@Service
public interface OfferEditionServiceInterface {

    OfferDto getOfferDtoById(Long id);

    void updateOffer(OfferDto editedOfferDto);
}
