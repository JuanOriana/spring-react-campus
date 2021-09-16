package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourseDao {
    Course create(Integer year, Integer quarter, String board, Integer subjectId, String subjectName,
                  String subjectCode);
    boolean update(Long id, Course course);
    boolean delete(Long id);
    List<Course> list(Long userId);
    Optional<Course> getById(Long id);
    Map<User, Role> getTeachers(Long courseId);
    public boolean belongs(Long userId, Long courseId);
}
