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
    boolean update(Integer id, Course course);
    boolean delete(Integer id);
    List<Course> list();
    Optional<Course> getById(Integer id);
    Map<User, Role> getTeachers(Integer courseId);
    public boolean belongs(Integer userId, Integer courseId);
}
