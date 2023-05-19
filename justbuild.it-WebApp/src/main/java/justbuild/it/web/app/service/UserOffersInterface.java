package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.Offer;
import justbuild.it.web.app.entity.User;

import java.util.List;

public interface UserOffersInterface {
    List<Offer> getUserOfferList(User user);

    boolean isUserOfferActive(Offer offer);
}
