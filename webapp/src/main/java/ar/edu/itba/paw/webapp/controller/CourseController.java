package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequestMapping("/course")
public class CourseController {

    @RequestMapping("/{courseId}")
    public ModelAndView announcements(@PathVariable int courseId) {
        final ModelAndView mav = new ModelAndView("course");
        mav.addObject("courseName", "Materia: " + courseId);
        return mav;
    }
}
