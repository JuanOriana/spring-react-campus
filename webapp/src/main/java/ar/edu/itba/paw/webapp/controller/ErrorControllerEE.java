package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorControllerEE {

    @RequestMapping(method = RequestMethod.GET, value = "400")
    public ModelAndView serverError4() {
        return new ModelAndView("400");
    }

    @RequestMapping(method = RequestMethod.GET, value = "403")
    public ModelAndView accessDenied() {
        return new ModelAndView("403");
    }

    @RequestMapping(method = RequestMethod.GET, value = "404")
    public ModelAndView noSuchPath() {
        return new ModelAndView("404");
    }

    @RequestMapping(method = RequestMethod.GET, value = "500")
    public ModelAndView serverError() {
        return new ModelAndView("500");
    }

}
