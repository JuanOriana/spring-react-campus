package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Timetable;

import java.sql.Time;
import java.util.List;

public interface TimetableDao {
    boolean create(Course course, int dayOfWeek, Time start, Time end);
    boolean update(int course_id, int dayOfWeek, Time start, Time end);
    boolean delete(int course_id);
    public List<Timetable> getById(long courseId);
}
