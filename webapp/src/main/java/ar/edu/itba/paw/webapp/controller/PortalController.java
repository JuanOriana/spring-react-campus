package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import java.util.List;

@Controller
public class PortalController {

    @Autowired
    CourseService courseService;

    @RequestMapping("/")
    public RedirectView rootRedirect() {
        return new RedirectView("portal");
    }

    @RequestMapping("/portal")
    public ModelAndView portal() {
        ModelAndView mav = new ModelAndView("portal");
        List<Course> courses = courseService.list();
        mav.addObject("courseList", courses);
        return mav;
    }

}
