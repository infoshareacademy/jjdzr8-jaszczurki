package justbuild.it.web.app.control.repository;

import justbuild.it.web.app.entity.entities.OfferEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OfferRepository {

    private final SessionFactory sessionFactory;

    public OfferRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(OfferEntity offer) {
        Session session = sessionFactory.getCurrentSession();
        session.save(offer);
    }

    public Optional<OfferEntity> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        OfferEntity offer = session.get(OfferEntity.class, id);
        return Optional.ofNullable(offer);
    }
}
