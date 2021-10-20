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
        if (dbTimetable.isEmpty()) return false;
        for (Timetable t : dbTimetable){
            t.setDayOfWeek(dayOfWeek);
            t.setBegins(start);
            t.setEnd(end);
        }
        return true;
    }

    @Override
    public boolean delete(Long courseId) {
        List<Timetable> timetables = findById(courseId);
        if(timetables.isEmpty()) return false;
        for(Timetable t : timetables) {
            em.remove(t);
        }
        return true;
    }

    @Override
    public List<Timetable> findById(Long courseId) {
        final TypedQuery<Timetable> query = em.createQuery("SELECT t FROM Timetable t WHERE t.course.courseId = :courseId", Timetable.class);
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }
}
