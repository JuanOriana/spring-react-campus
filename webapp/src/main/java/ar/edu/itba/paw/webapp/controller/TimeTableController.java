package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.TimetableService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Timetable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TimeTableController {

    @Autowired
    CourseService courseService;

    @Autowired
    TimetableService timetableService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeTableController.class);

    @RequestMapping("/timetable")
    public ModelAndView timeTable() {
        List<Course> courses = courseService.list();
        final String[] days = {"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
        final String[] hours = {"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00",
                "17:00","18:00","19:00","20:00"};
        Map<Course, List<Timetable>> courseTimetables = new HashMap<>();
        courses.forEach(c -> courseTimetables.put(c, timetableService.getById(c.getCourseId())));
        ModelAndView mav = new ModelAndView("timetable");
        mav.addObject("days",days);
        mav.addObject("hours",hours);
        return mav;
    }

}
