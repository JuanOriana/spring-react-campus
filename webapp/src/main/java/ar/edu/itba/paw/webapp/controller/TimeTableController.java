package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.TimetableService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Timetable;
import ar.edu.itba.paw.webapp.auth.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Time;
import java.util.*;

@Controller
public class TimeTableController extends AuthController{

    final String[] days = {"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
    final String[] hours = {"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00",
            "17:00","18:00","19:00","20:00","21:00","22:00"};
    final String[] colors = {"#2EC4B6","#173E5C","#0D6C52","#16386D","6F9A13"};

    private final CourseService courseService;
    private final TimetableService timetableService;

    @Autowired
    public TimeTableController(AuthFacade authFacade, TimetableService timetableService, CourseService courseService) {
        super(authFacade);
        this.timetableService = timetableService;
        this.courseService = courseService;
    }


    @RequestMapping("/timetable")
    public ModelAndView timeTable() {
        int colorIdx = 0;
        Map<Course,String> courseColors = new HashMap<>();
        Map<Course, List<Timetable>> courseTimetables = new HashMap<>();

        List<Course> courses = courseService.list(authFacade.getCurrentUser().getUserId());
        for (Course course: courses) {
            courseTimetables.put(course, timetableService.getById(course.getCourseId()));
            if (colorIdx >= colors.length) colorIdx = 0;
            courseColors.put(course,colors[colorIdx]);
            colorIdx++;
        }
        ArrayList<ArrayList<Course>> timeTableMatrix = createTimeTableMatrix(courseTimetables);
        ModelAndView mav = new ModelAndView("timetable");
        mav.addObject("days",days);
        mav.addObject("hours",hours);
        mav.addObject("timeTableMatrix",timeTableMatrix);
        mav.addObject("courseColors",courseColors);
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
                        timeTableMatrix.get(timetable.getDayOfWeek()).set(i,entry.getKey());
                    }
                }
            }
        }
        return timeTableMatrix;
    }

    private static Time stringToTime(String stringedTime){
        String[] tokens = stringedTime.split(":");
        return new Time(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]),0);

    }

}
