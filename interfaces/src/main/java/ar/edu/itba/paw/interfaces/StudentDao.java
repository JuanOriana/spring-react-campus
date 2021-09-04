package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Student;
import ar.edu.itba.paw.models.Teacher;

import java.util.List;
import java.util.Optional;

public interface StudentDao {
    Student create(Student student);
    boolean update(int id,Student student);
    boolean delete(int id);
    Optional<Student> getById(long id);
    List<Student> list();
}
