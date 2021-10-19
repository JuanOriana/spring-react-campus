package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.TimetableDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Timetable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class TimetableDaoJpa implements TimetableDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean create(Course course, int dayOfWeek, Time start, Time end) {
        final Timetable timetable = new Timetable(course, dayOfWeek, start, end);
        em.persist(timetable);
        return true;
    }

    @Override
    public boolean update(Long courseId, int dayOfWeek, Time start, Time end) {
        List<Timetable> dbTimetable = findById(courseId);
        if (dbTimetable.size() == 0) return false;
        for (Timetable t : dbTimetable){
            t.setDayOfWeek(dayOfWeek);
            t.setBegins(start);
            t.setEnd(end);
        }
        em.flush();
        return true;
    }

    @Override
    public boolean delete(Long courseId) {
        int deletedCount = em.createQuery("DELETE FROM Timetable t WHERE t.course = :courseId").setParameter("courseId", courseId).executeUpdate();
        return deletedCount > 0;
    }

    @Override
    public List<Timetable> findById(Long courseId) {
        final TypedQuery<Timetable> query = em.createQuery("SELECT t FROM Timetable t WHERE t.course = :courseId", Timetable.class);
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }
}
