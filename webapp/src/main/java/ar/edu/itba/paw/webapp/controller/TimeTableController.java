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

import java.sql.Time;
import java.util.*;

@Controller
public class TimeTableController {

    final String[] days = {"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
    final String[] hours = {"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00",
            "17:00","18:00","19:00","20:00","21:00","22:00"};

    @Autowired
    CourseService courseService;

    @Autowired
    TimetableService timetableService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeTableController.class);

    @RequestMapping("/timetable")
    public ModelAndView timeTable() {
        List<Course> courses = courseService.list();
        Map<Course, List<Timetable>> courseTimetables = new HashMap<>();
        courses.forEach(c -> courseTimetables.put(c, timetableService.getById(c.getCourseId())));
        ArrayList<ArrayList<Course>> timeTableMatrix = createTimeTableMatrix(courseTimetables);
        ModelAndView mav = new ModelAndView("timetable");
        mav.addObject("days",days);
        mav.addObject("hours",hours);
        mav.addObject("timeTableMatrix",timeTableMatrix);
        return mav;
    }

    private ArrayList<ArrayList<Course>> createTimeTableMatrix(Map<Course,List<Timetable>> timeMap){
        ArrayList<ArrayList<Course>> timeTableMatrix = new ArrayList<>();
        for (int i = 0; i < days.length; i++){
            timeTableMatrix.add(new ArrayList<>());
            for (int j = 0; j < hours.length; j++){
                timeTableMatrix.get(i).add(j,null);
            }
        }

        for (Map.Entry<Course,List<Timetable>> entry : timeMap.entrySet()){
            for (Timetable timetable : entry.getValue()){
                Time begins = timetable.getBegins();
                Time ends = timetable.getEnd();
                for (int i = 0; i < hours.length; i++){
                    Time timedHour = stringToTime(hours[i]);
                    if ((begins.before(timedHour) || begins.equals(timedHour)) && ends.after(timedHour)){
                        timeTableMatrix.get(timetable.getDayOfWeek()).add(i,entry.getKey());
                    }
                }
            }
        }
        return timeTableMatrix;
    }

    static private Time stringToTime(String stringedTime){
        String[] tokens = stringedTime.split(":");
        return new Time(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]),0);

    }

}
