package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import java.util.Optional;

public interface TimeTableDao {
    boolean create(Course course, int dayOfWeek, long start, long duration);
    boolean update(int course_id, int dayOfWeek, long start, long duration);
    boolean delete(int course_id);
    Optional<Integer> getDayOfWeekOfCourseById(long course_id);
    Optional<Long> getStartOfCourseById(long course_id);
    Optional<Long> getDurationOfCourseById(long course_id);
}
