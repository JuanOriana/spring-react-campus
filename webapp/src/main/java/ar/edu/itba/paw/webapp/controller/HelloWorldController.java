package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("greeting", userService.list().get(0).getName());
        return mav;
    }

    @RequestMapping("/course")
    public ModelAndView announcements() {
        final ModelAndView mav = new ModelAndView("course");
        mav.addObject("greeting", userService.list().get(0).getName());
        mav.addObject("courseName", "Materia");
        return mav;
    }
}
