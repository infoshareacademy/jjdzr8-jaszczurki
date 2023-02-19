package justbuild.it.web.app.controller;

import justbuild.it.web.app.service.WebAppService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebAppController {

    private final WebAppService webAppService;

    public WebAppController(WebAppService webAppService) {
        this.webAppService = webAppService;
    }

    @GetMapping("/home")
    public String goHome(Model model) {
      return "home";
    }

    @GetMapping("/add")
    public String goAdd(Model model) {
        return "add";
    }
}
