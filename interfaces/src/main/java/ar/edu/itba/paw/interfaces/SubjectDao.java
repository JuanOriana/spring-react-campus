package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectDao {
    Subject create(String code, String name);
    Optional<Subject> findById(Long subjectId);
    boolean update(Long subjectId, String code, String name);
    boolean delete(Long subjectId);
    List<Subject> list();
}
