package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Timetable;

import java.sql.Time;
import java.util.List;

public interface TimetableDao {
    boolean create(Course course, int dayOfWeek, Time start, Time end);
    boolean update(Long courseId, int dayOfWeek, Time start, Time end);
    boolean delete(Long courseId);
    public List<Timetable> getById(Long courseId);
}
