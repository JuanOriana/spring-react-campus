package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourseDao {
    Course create(Course course);
    boolean update(long id, Course course);
    boolean delete(long id);
    List<Course> list();
    Optional<Course> getById(long id);
    Map<User, Role> getTeachers(long courseId);
    public boolean belongs(long userId, long courseId);
}
