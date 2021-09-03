package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.TimeTableDao;
import ar.edu.itba.paw.interfaces.TimeTableService;
import ar.edu.itba.paw.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TimeTableServiceImpl implements TimeTableService {

    @Autowired
    private TimeTableDao timeTableDaoDao;

    @Override
    public boolean create(Course course, int dayOfWeek, long start, long duration) {
        return timeTableDaoDao.create(course, dayOfWeek, start, duration);
    }

    @Override
    public boolean update(int courseId, int dayOfWeek, long start, long duration) {
        return timeTableDaoDao.update(courseId, dayOfWeek, start, duration);
    }

    @Override
    public boolean delete(int courseId) {
        return timeTableDaoDao.delete(courseId);
    }

    @Override
    public Optional<Integer> getDayOfWeekOfCourseById(long courseId) {
        return timeTableDaoDao.getDayOfWeekOfCourseById(courseId);
    }

    @Override
    public Optional<Long> getStartOfCourseById(long courseId) {
        return timeTableDaoDao.getStartOfCourseById(courseId);
    }

    @Override
    public Optional<Long> getDurationOfCourseById(long courseId) {
        return timeTableDaoDao.getDurationOfCourseById(courseId);
    }
}
