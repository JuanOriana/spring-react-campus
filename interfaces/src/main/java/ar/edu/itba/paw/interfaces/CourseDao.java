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
    List<Course> listCurrent(Long userId);
    Optional<Course> findById(Long id);
    List<User> getStudents(Long courseId);
    Map<User, Role> getTeachers(Long courseId);
    boolean belongs(Long userId, Long courseId);
    boolean enroll(Long userId, Long courseId, Integer roleId);
    List<User> listUnenrolledUsers(Long courseId);
    List<Course> listWhereStudent(Long userId);
    List<Course> listByYearQuarter(Integer year, Integer quarter);
    List<Integer> getAvailableYears();
}
