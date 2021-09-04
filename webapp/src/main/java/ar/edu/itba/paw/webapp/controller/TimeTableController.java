package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.models.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class TimeTableController {

    @RequestMapping("/timetable")
    public ModelAndView timeTable() {
        final String[] days = {"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
        final String[] hours = {"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00",
                "17:00","18:00","19:00","20:00"};
        /* A matrix of days.length x hours.length course objects is needed, where null represents an hour in which
           there is no active course. */
        ModelAndView mav = new ModelAndView("timetable");
        mav.addObject("days",days);
        mav.addObject("hours",hours);
        return mav;
    }

}
