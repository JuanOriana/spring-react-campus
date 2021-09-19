package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Subject;

import java.util.List;


public interface SubjectService {
    Subject create(String code, String name);
    boolean update(Long subjectId, String code, String name);
    boolean delete(Long subjectId);
    List<Subject> list();

}
