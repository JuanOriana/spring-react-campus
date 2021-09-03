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
    public boolean update(int course_id, int dayOfWeek, long start, long duration) {
        return timeTableDaoDao.update(course_id, dayOfWeek, start, duration);
    }

    @Override
    public boolean delete(int course_id) {
        return timeTableDaoDao.delete(course_id);
    }

    @Override
    public Optional<Integer> getDayOfWeekOfCourseById(long course_id) {
        return timeTableDaoDao.getDayOfWeekOfCourseById(course_id);
    }

    @Override
    public Optional<Long> getStartOfCourseById(long course_id) {
        return timeTableDaoDao.getStartOfCourseById(course_id);
    }

    @Override
    public Optional<Long> getDurationOfCourseById(long course_id) {
        return timeTableDaoDao.getDurationOfCourseById(course_id);
    }
}
