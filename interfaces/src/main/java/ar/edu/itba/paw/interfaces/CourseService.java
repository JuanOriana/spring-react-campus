package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourseService {
    /**
     * Attempts to persist a course entry in the database
     * @param year of the course
     * @param quarter of the course
     * @param board code of the course
     * @param subjectId of the subject associated to the course
     * @param subjectName of the subject associated to the course
     * @param subjectCode of the subject associated to the course
     * @return a Course instance holding the passed values
     */
    Course create(Integer year, Integer quarter, String board, Integer subjectId, String subjectName,
                  String subjectCode);

    /**
     * Attempts to update a course
     * @param id of the course to be modified
     * @param course modified course
     * @return true if the course was successfully updated, false otherwise
     */
    boolean update(Integer id, Course course);

    /**
     * Attempts to delete a course
     * @param id of the course to be deleted
     * @return true if the course was successfully removed, false otherwise
     */
    boolean delete(Integer id);

    /**
     * Gets all the current available courses
     * @return list containing all the current available courses (if any)
     */
    List<Course> list();

    /**
     * Attempts to get a course given an id
     * @param courseId of the course to be retrieved
     * @return the course corresponding to the given id if it exists, null otherwise
     */
    Optional<Course> getById(Integer courseId);

    /**
     * Gets the map of teachers for the given course
     * @param courseId of the course to get the teachers from
     * @return map of Users as a key where the value is the teacher Role in that course
     */
    Map<User, Role> getTeachers(Integer courseId);

    /**
     * Returns if the user belongs to the course
     * @param userId of the user to check
     * @param courseId of the course to check
     * @return true if the user belongs to the given course, false otherwise
     */
    boolean belongs(Integer userId, Integer courseId);

}
