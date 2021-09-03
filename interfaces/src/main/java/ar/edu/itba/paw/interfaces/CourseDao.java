package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDao {
    boolean create(Course course);
    boolean update(long id, Course course);
    boolean delete(long id);
    List<Course> list();
    Optional<Course> getById(long id);
}
