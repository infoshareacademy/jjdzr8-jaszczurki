package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.User;
import justbuild.it.web.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
 
 private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);


    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void addUserFromForm(String username, String password) {

        LOGGER.debug("Adding user from form: username='{}'", username);

        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encodedPassword);
        newUser.setAuthorities(Collections.singleton(new SimpleGrantedAuthority("USER")));
        userRepository.save(newUser);
    }

    public User findUserByLogin(String username) {
        LOGGER.debug("Finding user by login: '{}'", username);
        return userRepository.findUserByUsername(username);
    }

    public Long getUserIdByUsername(String username) {
        LOGGER.debug("Getting user ID by username: '{}'", username);
        User user = userRepository.findUserByUsername(username);
        return user != null ? user.getUserId() : null;
    }
}

