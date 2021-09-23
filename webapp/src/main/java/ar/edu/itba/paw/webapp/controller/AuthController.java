package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.auth.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AuthController {

    protected final AuthFacade authFacade;

    @Autowired
    public AuthController(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @ModelAttribute
    public void getCurrentUser(Model model) {
        model.addAttribute("currentUser",
                authFacade.getCurrentUser());
    }
}
