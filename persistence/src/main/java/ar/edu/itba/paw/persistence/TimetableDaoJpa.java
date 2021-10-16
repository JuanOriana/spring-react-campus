package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.TimetableDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Timetable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Time;
import java.util.List;

@Primary
@Repository
public class TimetableDaoJpa implements TimetableDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean create(Course course, int dayOfWeek, Time start, Time end) {
        return false;
    }

    @Override
    public boolean update(Long courseId, int dayOfWeek, Time start, Time end) {
        return false;
    }

    @Override
    public boolean delete(Long courseId) {
        return false;
    }

    @Override
    public List<Timetable> findById(Long courseId) {
        return null;
    }
}
