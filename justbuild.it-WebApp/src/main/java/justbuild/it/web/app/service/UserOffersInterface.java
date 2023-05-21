package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.Offer;

import java.util.List;

public interface UserOffersInterface {

    List<Offer> getUserOfferList(Long userId);

    boolean isUserOfferActive(Offer offer);
}
