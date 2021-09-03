package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;

import java.util.Optional;

public interface TimeTableService {
    /**
     * Attempts to persist a timetable entry in the database
     * @param course The course to be persisted in the database
     * @return true if the course was successfully added, false otherwise
     */
    boolean create(Course course, int dayOfWeek, long start, long duration);

    /**
     * Attempts to update a course's timetable
     * @param course_id of the courses timetable to be modified
     * @param dayOfWeek modified
     * @param start modified
     * @param duration modified
     * @return true if the timetable was successfully updated, false otherwise
     */
    boolean update(int course_id, int dayOfWeek, long start, long duration);

    /**
     * Attempts to delete a course's timetable
     * @param course_id of the course's timetable to be deleted
     * @return true if the timetable was successfully removed, false otherwise
     */
    boolean delete(int course_id);

    /**
     * Attempts to get the day of the week of the timetable given a course id
     * @param course_id of the course's day to be retrieved
     * @return the day corresponding to the given id if it exists, null otherwise
     */
    Optional<Integer> getDayOfWeekOfCourseById(long course_id);

    /**
     * Attempts to get the start of the course extracted from its timetable given its course id
     * @param course_id of the course's start time (in millis) to be retrieved
     * @return the start time (in millis) corresponding to the given id if it exists, null otherwise
     */
    Optional<Long> getStartOfCourseById(long course_id);

    /**
     * Attempts to get the duration of a course extracted from its timetable given its course id
     * @param course_id of the course's duration to be retrieved
     * @return the duration corresponding to the given id if it exists, null otherwise
     */
    Optional<Long> getDurationOfCourseById(long course_id);
}
