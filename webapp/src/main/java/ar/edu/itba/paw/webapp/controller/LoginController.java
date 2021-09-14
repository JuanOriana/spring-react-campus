package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PortalController.class);

    @RequestMapping("/login")
    public ModelAndView portal() {
        return new ModelAndView("login");
    }
}
