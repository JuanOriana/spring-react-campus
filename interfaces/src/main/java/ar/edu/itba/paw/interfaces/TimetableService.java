package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Schedule;
import ar.edu.itba.paw.models.Timetable;

import java.util.Optional;

public interface ScheduleService {
    /**
     * Attempts to persist a timetable entry in the database
     * @param course The course to be persisted in the database
     * @return true if the course was successfully added, false otherwise
     */
    boolean create(Course course, int dayOfWeek, long start, long duration);

    /**
     * Attempts to update a course's timetable
     * @param courseId of the courses timetable to be modified
     * @param dayOfWeek modified
     * @param start modified
     * @param duration modified
     * @return true if the timetable was successfully updated, false otherwise
     */
    boolean update(int courseId, int dayOfWeek, long start, long duration);

    /**
     * Attempts to delete a course's timetable
     * @param courseId of the course's timetable to be deleted
     * @return true if the timetable was successfully removed, false otherwise
     */
    boolean delete(int courseId);

    /**
     * Attempts to get the timetable of the given id
     * @param courseId of the course to get the timetable from
     * @return schedule containing a list of timetables for the given courseId
     */
    Optional<Schedule> getById(int courseId);

}
