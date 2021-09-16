package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Timetable;

import java.sql.Time;
import java.util.List;

public interface TimetableDao {
    boolean create(Course course, int dayOfWeek, Time start, Time end);
    boolean update(Long course_id, int dayOfWeek, Time start, Time end);
    boolean delete(Long course_id);
    public List<Timetable> getById(Long courseId);
}
