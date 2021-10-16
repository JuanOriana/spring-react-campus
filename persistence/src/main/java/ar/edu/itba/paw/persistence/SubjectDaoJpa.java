package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SubjectDao;
import ar.edu.itba.paw.models.Subject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Primary
@Repository
public class SubjectDaoJpa implements SubjectDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Subject create(String code, String name) {
        return null;
    }

    @Override
    public boolean update(Long subjectId, String code, String name) {
        return false;
    }

    @Override
    public boolean delete(Long subjectId) {
        return false;
    }

    @Override
    public List<Subject> list() {
        return null;
    }
}
