package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Timetable;

import java.sql.Time;
import java.util.List;


public interface TimetableService {
    /**
     * Attempts to persist a timetable entry in the database
     * @param course The course to be persisted in the database
     * @return true if the course was successfully added, false otherwise
     */
    boolean create(Course course, int dayOfWeek, Time start, Time end);

    /**
     * Attempts to update a course's timetable
     * @param courseId of the courses timetable to be modified
     * @param dayOfWeek modified
     * @param start modified
     * @param end modified
     * @return true if the timetable was successfully updated, false otherwise
     */
    boolean update(Long courseId, int dayOfWeek, Time start, Time end);

    /**
     * Attempts to delete a course's timetable
     * @param courseId of the course's timetable to be deleted
     * @return true if the timetable was successfully removed, false otherwise
     */
    boolean delete(Long courseId);

    /**
     * Attempts to get the timetable of the given id
     * @param courseId of the course to get the timetable from
     * @return list of timetables for the given courseId
     */
    List<Timetable> getById(Long courseId);

}
