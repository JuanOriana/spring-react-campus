package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Teacher;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

public interface CourseDao {
    Course create(Course course);
    boolean update(long id, Course course);
    boolean delete(long id);
    List<Course> list();
    Optional<Course> getById(long id);
    List<Pair<Teacher, String>> getTeachersFromCourse(long courseId);
    boolean addTeacherToCourse(long teacherId, long courseId, String rol);
}
