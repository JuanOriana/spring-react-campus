package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class UserController  extends AuthController{

    private static final Logger LOGGER = LoggerFactory.getLogger(PortalController.class);

    @Autowired
    protected AuthFacade authFacade;

    @RequestMapping("/user")
    public ModelAndView portal() {
        return new ModelAndView("user");
    }

}
