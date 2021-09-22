package ar.edu.itba.paw.webapp.controller;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController extends AuthController {

    public UserController(AuthFacade authFacade) {
        super(authFacade);
    }

    @RequestMapping("/user")
    public ModelAndView portal() {
        return new ModelAndView("user");
    }

}
