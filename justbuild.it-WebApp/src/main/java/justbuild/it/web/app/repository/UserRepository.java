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

}