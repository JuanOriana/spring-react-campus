package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exception.CourseNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    AnnouncementService announcementService;

    @Autowired
    CourseService courseService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);
    private final Comparator<Announcement> orderByDate = (o1, o2) -> o2.getDate().compareTo(o1.getDate());


    @ExceptionHandler(CourseNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView noSuchCourse() {
        ModelAndView mav = new ModelAndView("errorPage");
        mav.addObject("errorMsg", "Course does not exist");
        return mav;
    }

    @RequestMapping("/{courseId}")
    public ModelAndView announcements(@PathVariable int courseId) {
        final ModelAndView mav = new ModelAndView("course");
        List<Announcement> announcements = announcementService.listByCourse(courseId,orderByDate);
        // Add proper handling in the future, need to check if user has permission to access this course
        mav.addObject("course", courseService.getById(courseId).orElseThrow(CourseNotFoundException::new));
        mav.addObject("announcementList", announcements);
        return mav;
    }

    @RequestMapping("/{courseId}/professors")
    public ModelAndView professors(@PathVariable int courseId) {
        final ModelAndView mav = new ModelAndView("professors");
        Map<User, Role> teachers = courseService.getTeachers(courseId);
        Set teacherSet = teachers.entrySet();
//        for (Map.Entry<User,Role> entry : teacherSet){
//            entry.getKey().
//        }
        mav.addObject("course", courseService.getById(courseId).orElseThrow(CourseNotFoundException::new));
        mav.addObject("teacherSet",teacherSet);
        return mav;
    }
}
