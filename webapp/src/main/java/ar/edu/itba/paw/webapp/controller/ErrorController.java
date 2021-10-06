package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @GetMapping(value = "400")
    public ModelAndView serverError4() {
        return new ModelAndView("400");
    }

    @GetMapping(value = "403")
    public ModelAndView accessDenied() {
        return new ModelAndView("403");
    }

    @GetMapping(value = "404")
    public ModelAndView noSuchPath() {
        return new ModelAndView("404");
    }

    @GetMapping(value = "500")
    public ModelAndView serverError() {
        return new ModelAndView("500");
    }

}
