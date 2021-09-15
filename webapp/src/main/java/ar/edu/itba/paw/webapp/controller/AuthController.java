package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.auth.AuthFacade;
import ar.edu.itba.paw.webapp.auth.CampusUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AuthController {

    @Autowired
    AuthFacade authFacade;

    @ModelAttribute
    public void getCurrentUser(Model model){
        model.addAttribute("currentUser",authFacade.getCurrentUser());
    }
}
