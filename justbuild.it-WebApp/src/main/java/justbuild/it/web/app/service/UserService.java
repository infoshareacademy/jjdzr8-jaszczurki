package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.User;
import justbuild.it.web.app.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUserFromForm(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(email, encodedPassword, Collections.singleton(new SimpleGrantedAuthority("USER")));
        userRepository.save(newUser);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmailAddress(email);
    }
}
