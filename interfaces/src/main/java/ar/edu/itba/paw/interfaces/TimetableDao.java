package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Timetable;
import java.util.List;

public interface TimetableDao {
    boolean create(Course course, int dayOfWeek, long start, long duration);
    boolean update(int course_id, int dayOfWeek, long start, long duration);
    boolean delete(int course_id);
    public List<Timetable> getById(int courseId);
}
