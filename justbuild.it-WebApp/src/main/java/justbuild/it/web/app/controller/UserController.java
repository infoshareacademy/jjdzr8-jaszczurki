package justbuild.it.web.app.controller;

import justbuild.it.web.app.entity.User;
import justbuild.it.web.app.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        }

        String username = user.getUsername();
        User existingUser = userService.findUserByLogin(username);
        if(existingUser != null) {
            result.rejectValue("username", "error.user", "User already exists");
            return "registration";
        }

        String password = user.getPassword();
        userService.addUserFromForm(username, password);
        return "redirect:/login_form";
    }

    @GetMapping("/login_form")
    public String showLogin() {
        return "loginForm";
    }

    @PostMapping("/login_form")
    public String processLoginForm(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/";
    }

    @GetMapping("/logout/logout_form")
    public String showLogout() {
        return "logoutForm";
    }
}
