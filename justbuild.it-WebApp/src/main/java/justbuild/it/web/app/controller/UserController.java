package justbuild.it.web.app.controller;

import justbuild.it.web.app.entity.User;
import justbuild.it.web.app.service.UserService;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@Valid @ModelAttribute("user") User user, @RequestParam String email, @RequestParam String password, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration";
        }

        User existingUser = userService.findUserByEmail(email);
        if(existingUser != null) {
            model.addAttribute("error", "User already exists");
            return "registration";
        }
        userService.addUserFromForm(email, password);
        return "redirect:/login_form?registered=true";
    }
}
