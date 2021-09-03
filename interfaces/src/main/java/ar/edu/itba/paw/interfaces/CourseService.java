package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    /**
     * Attempts to persist a course entry in the database
     * @param course The course to be persisted in the database
     * @return true if the course was successfully added, false otherwise
     */
    boolean create(Course course);

    /**
     * Attempts to update a course
     * @param id of the course to be modified
     * @param course modified course
     * @return true if the course was successfully updated, false otherwise
     */
    boolean update(long id, Course course);

    /**
     * Attempts to delete a course
     * @param id of the course to be deleted
     * @return true if the course was successfully removed, false otherwise
     */
    boolean delete(long id);

    /**
     * Gets all the current available courses
     * @return list containing all the current available courses (if any)
     */
    List<Course> list();

    /**
     * Attempts to get a course given an id
     * @param id of the course to be retrieved
     * @return the course corresponding to the given id if it exists, null otherwise
     */
    Optional<Course> getById(long id);
}
