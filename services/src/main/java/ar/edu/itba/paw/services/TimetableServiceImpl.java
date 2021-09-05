package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.TimetableDao;
import ar.edu.itba.paw.interfaces.TimetableService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Timetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableServiceImpl implements TimetableService {

    @Autowired
    private TimetableDao timetableDaoDao;

    @Override
    public boolean create(Course course, int dayOfWeek, long start, long duration) {
        return timetableDaoDao.create(course, dayOfWeek, start, duration);
    }

    @Override
    public boolean update(int courseId, int dayOfWeek, long start, long duration) {
        return timetableDaoDao.update(courseId, dayOfWeek, start, duration);
    }

    @Override
    public boolean delete(int courseId) {
        return timetableDaoDao.delete(courseId);
    }

    @Override
    public List<Timetable> getById(int courseId) {
        return timetableDaoDao.getById(courseId);
    }


}
