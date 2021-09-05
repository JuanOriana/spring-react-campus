package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.ScheduleDao;
import ar.edu.itba.paw.interfaces.ScheduleService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleDao scheduleDaoDao;

    @Override
    public boolean create(Course course, int dayOfWeek, long start, long duration) {
        return scheduleDaoDao.create(course, dayOfWeek, start, duration);
    }

    @Override
    public boolean update(int courseId, int dayOfWeek, long start, long duration) {
        return scheduleDaoDao.update(courseId, dayOfWeek, start, duration);
    }

    @Override
    public boolean delete(int courseId) {
        return scheduleDaoDao.delete(courseId);
    }

    @Override
    public Optional<Schedule> getById(int courseId) {
        return scheduleDaoDao.getById(courseId);
    }


}
