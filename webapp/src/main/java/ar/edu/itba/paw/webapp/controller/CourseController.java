package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    CourseService courseService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @RequestMapping("/{courseId}")
    public ModelAndView announcements(@PathVariable int courseId) {
        final ModelAndView mav;
        List<Announcement> announcements = announcementService.listByCourse(courseId);
        courseService.create(new Course(2, "probando"));
        // Add proper handling in the future, need to check if user has permission to access this course
        Optional<Course> course = courseService.getById(courseId);
        if(course.isPresent()) {
            mav = new ModelAndView("course");
            mav.addObject("course", course.get());
            mav.addObject("announcementList", announcements);
        } else {
            mav = new ModelAndView("errorPage");
            mav.addObject("errorMsg", "Course does not exist");
        }
        return mav;
    }
}
