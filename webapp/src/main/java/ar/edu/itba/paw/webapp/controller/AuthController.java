package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class AuthController {

    protected final AuthFacade authFacade;

    @Autowired
    private UserService userService;

    @Autowired
    public AuthController(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @ModelAttribute
    public void getCurrentUser(Model model) {
        model.addAttribute("currentUser",
                authFacade.getCurrentUser());
    }

    @ModelAttribute
    public void isUserImageSet(Model model) {
        Optional<byte[]> optionalImage = userService.getProfileImage(authFacade.getCurrentUser().getUserId());
        model.addAttribute("isImageSet",optionalImage.isPresent());
    }
}
