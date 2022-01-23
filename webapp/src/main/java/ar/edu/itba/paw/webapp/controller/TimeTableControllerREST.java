package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.TimetableService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Timetable;
import ar.edu.itba.paw.webapp.dto.CourseDto;
import ar.edu.itba.paw.webapp.security.service.AuthFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("timetable")
@Component
public class TimeTableControllerREST {

    private static final String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    private static final String[] hours = {"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00",
            "17:00","18:00","19:00","20:00","21:00","22:00"};
    //TODO: ver si estas variables siguen teniendo sentido que sean asi o las pasamos a ints

    @Autowired
    private CourseService courseService;

    @Autowired
    private TimetableService timetableService;

    @Autowired
    private AuthFacade authFacade;

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, })
    public Response timeTable() throws JsonProcessingException {
        Map<Course, List<Timetable>> courseTimetables = new HashMap<>();

        List<Course> courses = courseService.listCurrent(authFacade.getCurrentUserId());

        for (Course course: courses) {
            courseTimetables.put(course, timetableService.findById(course.getCourseId()));
        }

        HashMap<Integer, List<CourseDto>> timeTableMatrix = createTimeTableMatrix(courseTimetables);

        return Response.ok(new ObjectMapper().writeValueAsString(timeTableMatrix)).build();
    }

    private HashMap<Integer,List<CourseDto>> createTimeTableMatrix(Map<Course,List<Timetable>> timeMap){
        HashMap<Integer,List<CourseDto>> timeTableMatrix = new HashMap<>();
        for (int i = 0; i < days.length; i++){
            timeTableMatrix.put(i,new ArrayList<>());
            for (int j = 0; j < hours.length; j++){
                timeTableMatrix.get(i).add(j,null);
            }
        }

        for (Map.Entry<Course,List<Timetable>> entry : timeMap.entrySet()) {
            for (Timetable timetable : entry.getValue()){
                LocalTime begins = timetable.getBegins();
                LocalTime ends = timetable.getEnd();
                for (int i = 0; i < hours.length; i++){
                    LocalTime timedHour = stringToTime(hours[i]);
                    if ((begins.isBefore(timedHour) || begins.equals(timedHour)) && ends.isAfter(timedHour)){
                        timeTableMatrix.get(timetable.getDayOfWeek()).add(i,CourseDto.fromCourse(entry.getKey()));
                    }
                }
            }
        }
        return timeTableMatrix;
    }

    private static LocalTime stringToTime(String stringedTime){
        String[] tokens = stringedTime.split(":");
        return LocalTime.of(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]));

    }


}
