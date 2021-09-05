package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Schedule;

import java.util.Optional;

public interface ScheduleDao {
    boolean create(Course course, int dayOfWeek, long start, long duration);
    boolean update(int course_id, int dayOfWeek, long start, long duration);
    boolean delete(int course_id);
    public Optional<Schedule> getById(int courseId);
}
