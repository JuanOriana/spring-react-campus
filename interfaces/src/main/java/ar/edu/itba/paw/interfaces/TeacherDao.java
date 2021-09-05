package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Teacher;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

public interface TeacherDao {
    Teacher create(Teacher teacher);
    boolean update(int id,Teacher teacher);
    boolean delete(int id);
    Optional<Teacher> getById(long id);
    List<Teacher> list();
    List<Pair<Course,String>> getTeacherCourses(int teacherId);

}