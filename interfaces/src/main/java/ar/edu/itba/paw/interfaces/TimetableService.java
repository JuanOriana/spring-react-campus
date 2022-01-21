package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Timetable;
import java.time.LocalTime;
import java.util.List;


public interface TimetableService {
    /**
     * Attempts to persist a timetable entry in the database
     *
     * @param course The course to be persisted in the database
     * @return true if the course was successfully added, false otherwise
     */
    boolean create(Course course, int dayOfWeek, LocalTime start, LocalTime end);

    /**
     * Attempts to update a course's timetable
     *
     * @param courseId  of the courses timetable to be modified
     * @param dayOfWeek modified
     * @param start     modified
     * @param end       modified
     * @return true if the timetable was successfully updated, false otherwise
     */
    boolean update(Long courseId, int dayOfWeek, LocalTime start, LocalTime end);

    /**
     * Attempts to delete a course's timetable
     *
     * @param courseId of the course's timetable to be deleted
     * @return true if the timetable was successfully removed, false otherwise
     */
    boolean delete(Long courseId);

    /**
     * Attempts to get the timetable of the given id
     *
     * @param courseId of the course to get the timetable from
     * @return list of timetables for the given courseId
     */
    List<Timetable> findById(Long courseId);

    /**
     * Attempts to get the timetable start times of the given id
     *
     * @param courseId of the course to get the timetable from
     * @return list of timetables start times for the given courseId
     */
    List<Integer> getStartTimesOf(Long courseId);

    /**
     * Attempts to get the timetable end times of the given id
     *
     * @param courseId of the course to get the timetable from
     * @return list of timetables end times for the given courseId
     */
    List<Integer> getEndTimesOf(Long courseId);

    /**
     * Attempts to get the timetable of the given id ordered in each day
     *
     * @param courseId of the course to get the timetable from
     * @return array of timetables for the given courseId
     */
    Timetable[] findByIdOrdered(Long courseId);
}
