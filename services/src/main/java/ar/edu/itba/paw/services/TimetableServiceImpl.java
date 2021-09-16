package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.TimetableDao;
import ar.edu.itba.paw.interfaces.TimetableService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Timetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

@Service
public class TimetableServiceImpl implements TimetableService {

    @Autowired
    private TimetableDao timetableDaoDao;

    @Override
    public boolean create(Course course, int dayOfWeek, Time start, Time end) {
        return timetableDaoDao.create(course, dayOfWeek, start, end);
    }

    @Override
    public boolean update(Long courseId, int dayOfWeek, Time start, Time end) {
        return timetableDaoDao.update(courseId, dayOfWeek, start, end);
    }

    @Override
    public boolean delete(Long courseId) {
        return timetableDaoDao.delete(courseId);
    }

    @Override
    public List<Timetable> getById(Long courseId) {
        return timetableDaoDao.getById(courseId);
    }


}
