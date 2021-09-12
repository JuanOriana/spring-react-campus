package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.AnnouncementService;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exception.CourseNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.*;

@Controller
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

    @RequestMapping("/course/{courseId}")
    public ModelAndView announcements(@PathVariable int courseId) {
        final ModelAndView mav = new ModelAndView("course");
        List<Announcement> announcements = announcementService.listByCourse(courseId,orderByDate);
        // Add proper handling in the future, need to check if user has permission to access this course
        mav.addObject("course", courseService.getById(courseId).orElseThrow(CourseNotFoundException::new));
        mav.addObject("announcementList", announcements);
        return mav;
    }

    @RequestMapping(value = "/teacher-course/{courseId}", method = RequestMethod.GET)
    public ModelAndView teacherAnnouncements(@PathVariable int courseId) {
        final ModelAndView mav = new ModelAndView("teacher/teacher-course");
        List<Announcement> announcements = announcementService.listByCourse(courseId,orderByDate);
        // Add proper handling in the future, need to check if user has permission to access this course
        mav.addObject("course", courseService.getById(courseId).orElseThrow(CourseNotFoundException::new));
        mav.addObject("announcementList", announcements);
        return mav;
    }

    @RequestMapping(value = "/teacher-course/{courseId}", method = RequestMethod.POST)
    public ModelAndView teacherAnnouncements(@PathVariable int courseId,
                                             @RequestParam(value = "title", required = true) final String title,
                                             @RequestParam(value = "content", required = true) final String content){
        //TODO: GETTING A RANDOM TEACHER CHANGE LATER
        Set<User> teacherSet = courseService.getTeachers(courseId).keySet();
        announcementService.create(new Announcement(LocalDateTime.now(),
                title,content,teacherSet.iterator().next(),courseService.getById(courseId).get()));
        final ModelAndView mav = new ModelAndView("teacher/teacher-course");
        List<Announcement> announcements = announcementService.listByCourse(courseId,orderByDate);
        // Add proper handling in the future, need to check if user has permission to access this course
        mav.addObject("course", courseService.getById(courseId).orElseThrow(CourseNotFoundException::new));
        mav.addObject("announcementList", announcements);
        return mav;
    }

    @RequestMapping("/course/{courseId}/teachers")
    public ModelAndView professors(@PathVariable int courseId) {
        final ModelAndView mav = new ModelAndView("teachers");
        Map<User, Role> teachers = courseService.getTeachers(courseId);
        Set<Map.Entry<User,Role>> teacherSet = teachers.entrySet();
        mav.addObject("course", courseService.getById(courseId).orElseThrow(CourseNotFoundException::new));
        mav.addObject("teacherSet",teacherSet);
        return mav;
    }

    @RequestMapping("/course/{courseId}/files")
    public ModelAndView files(@PathVariable int courseId) {
        final ModelAndView mav = new ModelAndView("course-files");
        mav.addObject("course", courseService.getById(courseId).orElseThrow(CourseNotFoundException::new));
        return mav;
    }

    @RequestMapping("/teacher-course/{courseId}/files")
    public ModelAndView teacherFiles(@PathVariable int courseId) {
        final ModelAndView mav = new ModelAndView("teacher/teacher-files");
        mav.addObject("course", courseService.getById(courseId).orElseThrow(CourseNotFoundException::new));
        return mav;
    }
}
