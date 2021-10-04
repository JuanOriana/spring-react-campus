package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.*;
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
     * @return a Course instance holding the passed values
     */
    Course create(Integer year, Integer quarter, String board, Long subjectId, List<Integer> startTimes,
                                                   List<Integer> endTimes);

    /**
     * Attempts to update a course
     * @param id of the course to be modified
     * @param course modified course
     * @return true if the course was successfully updated, false otherwise
     */
    boolean update(Long id, Course course);

    /**
     * Attempts to delete a course
     * @param id of the course to be deleted
     * @return true if the course was successfully removed, false otherwise
     */
    boolean delete(Long id);

    /**
     * Gets all available courses from the course table
     * @return list containing all the courses (if any)
     */
    List<Course> list();

    /**
     * Gets all the available courses of a user
     * @param userId of the user to retrieve the courses from
     * @return list containing all the current available courses (if any)
     */
    List<Course> list(Long userId);

    /**
     * Gets all the current available courses of a user
     * @param userId of the user to retrieve the courses from
     * @return list containing all the current available courses (if any)
     */
    List<Course> listCurrent(Long userId);

    /**
     * Attempts to get a course given an id
     * @param courseId of the course to be retrieved
     * @return the course corresponding to the given id if it exists, null otherwise
     */
    Optional<Course> getById(Long courseId);

    /**
     * Gets the map of teachers for the given course
     * @param courseId of the course to get the teachers from
     * @return map of Users as a key where the value is the teacher Role in that course
     */
    Map<User, Role> getTeachers(Long courseId);

    /**
     * Gets the list of students for the given course
     * @param courseId of the course to get the teachers from
     * @return List of Users in that course
     */
    List<User> getStudents(Long courseId);

    /**
     * Informs if the user belongs to the course
     * @param userId of the user to check
     * @param courseId of the course to check
     * @return true if the user belongs to the given course, false otherwise
     */
    boolean belongs(Long userId, Long courseId);

    /**
     * Informs if the user is a helper or a teacher in the current course
     * @param userId of the user to check the privilege
     * @param courseId of the course to check the privilege
     * @return true if the user is a helper or a teacher in the current course, false otherwise
     */
    boolean isPrivileged(Long userId, Long courseId);

    /**
     * Attempts to persist a user to a course with the given role
     * @param userId of the user to enroll
     * @param courseId of the course to enroll the user to
     * @param roleId of the role of the user in the course
     */
    void enroll(Long userId, Long courseId, Integer roleId);

    /**
     * Gets a list of Users not enrolled in a course
     * @param courseId of the queried course
     * @return list of Users not enrolled in a course
     */
    List<User> listUnenrolledUsers(Long courseId);

    /**
     * Attempts to get all courses where the user is a student
     * @param userId of the queried user
     * @return a list of courses where the given user is a student
     */
    List<Course> listWhereStudent(Long userId);

    /**
     * Attempts to get all courses in the pair year-quarter
     * @param year to get the courses from
     * @param quarter to get the courses from
     * @return list of courses for the specific year and quarter
     */
    List<Course> listByYearQuarter(Integer year, Integer quarter);

    /**
     * Attempts to get a list of years where there are courses present
     * @return list of years where there are courses present
     */
    List<Integer> getAvailableYears();

}
