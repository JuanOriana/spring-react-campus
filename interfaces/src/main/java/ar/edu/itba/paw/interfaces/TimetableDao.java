package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Timetable;

import java.time.LocalTime;
import java.util.List;

public interface TimetableDao {
    boolean create(Course course, int dayOfWeek, LocalTime start, LocalTime end);

    boolean update(Long courseId, int dayOfWeek, LocalTime start, LocalTime end);

    boolean delete(Long courseId);

    List<Timetable> findById(Long courseId);
}
