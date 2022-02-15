package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.Subject;

import java.util.List;
import java.util.Optional;


public interface SubjectService {
    /**
     * Attempts to persist a subject entry in the database
     * @param code of the subject
     * @param name of the subject
     * @return a Subject instance holding the passed values
     */
    Subject create(String code, String name);

    /**
     * Attempts to update a subject
     * @param subjectId of the subject to be modified
     * @param code to be changed to
     * @param name to be changed to
     * @return true if the subject was successfully updated, false otherwise
     */
    boolean update(Long subjectId, String code, String name);

    /**
     * Attempts to find a subject based on the provided id
     * @param subjectId of the queried subject
     * @return Subject if any id matches the search, Optional.empty() otherwise
     */
    Optional<Subject> findById(Long subjectId);

    /**
     * Attempts to delete a subject
     * @param subjectId of the subject to be deleted
     * @return true if the subject was successfully removed, false otherwise
     */
    boolean delete(Long subjectId);

    /**
     * Gets all available subjects from the course table
     * @return list containing all the subjects (if any)
     */
    List<Subject> list();

    CampusPage<Subject> list(Integer page, Integer pageSize);

}
