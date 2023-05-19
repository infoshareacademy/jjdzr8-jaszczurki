package justbuild.it.web.app.repository;

import justbuild.it.web.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User newUser);

    @Query("SELECT u FROM User u JOIN FETCH u.authorities WHERE u.username = :username")
    User findUserByUsername(@Param("username") String username);

    @Query("SELECT u.userId FROM User u WHERE u.username = :username")
    Long findUserIdByUsername(@Param("username") String username);

    @Query("SELECT COUNT(o) > 0 FROM User u JOIN u.offers o WHERE u.userId = :user_id AND o.offerId = :offer_id")
    boolean isUserOwnerOfOffer(@Param("user_id") Long userId, @Param("offer_id") Long offerId);
}
