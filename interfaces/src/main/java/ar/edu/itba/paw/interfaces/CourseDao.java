package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourseDao {
    Course create(Integer year, Integer quarter, String board, Long subjectId);
    boolean update(Long id, Course course);
    boolean delete(Long id);
    List<Course> list();
    List<Course> list(Long userId);
    Optional<Course> getById(Long id);
    Optional<Course> getBy(Long subjectId, Integer year, Integer quarter, String board);
    List<User> getStudents(Long courseId);
    Map<User, Role> getTeachers(Long courseId);
    boolean belongs(Long userId, Long courseId);
    boolean enroll(Long userId, Long courseId, Integer roleId);
    List<User> listUnenrolledUsers(Long courseId);
    List<Course> getCoursesWhereStudent(Long userId);
}
