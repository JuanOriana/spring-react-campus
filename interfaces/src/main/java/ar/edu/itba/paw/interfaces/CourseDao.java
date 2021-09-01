package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDao {
    boolean create(Course course);
    boolean update(int id, Course course);
    boolean delete(int id);
    List<Course> list();
    Optional<Course> getById(int id);
}
