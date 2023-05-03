package justbuild.it.web.app.repository;

import justbuild.it.web.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    void save(User newUser);
    User findByEmailAddress(String email);
}

