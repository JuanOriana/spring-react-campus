package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SubjectDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Subject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class SubjectDaoJpa implements SubjectDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Subject create(String code, String name) {
        final Subject subject = new Subject(code, name);
        em.persist(subject);
        return subject;
    }

    @Override
    public boolean update(Long subjectId, String code, String name) {
        Optional<Subject> dbSubject = Optional.ofNullable(em.find(Subject.class, subjectId));
        if(!dbSubject.isPresent()) return false;
        dbSubject.get().merge(new Subject(code,name));
        return true;
    }

    @Override
    public boolean delete(Long subjectId) {
        Optional<Subject> dbSubject = Optional.ofNullable(em.find(Subject.class, subjectId));
        if(!dbSubject.isPresent()) return false;
        em.remove(dbSubject.get());
        return true;
    }

    @Override
    public List<Subject> list() {
        final TypedQuery<Subject> query = em.createQuery("SELECT s FROM Subject s", Subject.class);
        return query.getResultList();
    }


}
