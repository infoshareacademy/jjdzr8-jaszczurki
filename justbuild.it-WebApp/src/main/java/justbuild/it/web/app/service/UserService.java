package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.User;
import justbuild.it.web.app.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void addUserFromForm(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encodedPassword);
        newUser.setAuthorities(Collections.singleton(new SimpleGrantedAuthority("USER")));
        userRepository.save(newUser);
    }

    public User findUserByLogin(String username) {
        return userRepository.findUserByUsername(username);
    }

    public Long getUserIdByUsername(String username) {
        User user = userRepository.findUserByUsername(username);
        return user != null ? user.getUserId() : null;
    }

    public boolean isUserOwnerOfOffer(Long userId, Long offerId) {
        return userRepository.isUserOwnerOfOffer(userId, offerId);
    }
}
