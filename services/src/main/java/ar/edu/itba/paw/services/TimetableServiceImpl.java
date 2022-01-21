package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.TimetableDao;
import ar.edu.itba.paw.interfaces.TimetableService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Timetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimetableServiceImpl implements TimetableService {

    private static final String[] hours = {"08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00",
            "17:00","18:00","19:00","20:00","21:00","22:00"};

    private final TimetableDao timetableDaoDao;

    @Autowired
    public TimetableServiceImpl(TimetableDao timetableDaoDao) {
        this.timetableDaoDao = timetableDaoDao;
    }

    @Transactional
    @Override
    public boolean create(Course course, int dayOfWeek, LocalTime start, LocalTime end) {
        return timetableDaoDao.create(course, dayOfWeek, start, end);
    }

    @Transactional
    @Override
    public boolean update(Long courseId, int dayOfWeek, LocalTime start, LocalTime end) {
        return timetableDaoDao.update(courseId, dayOfWeek, start, end);
    }

    @Transactional
    @Override
    public boolean delete(Long courseId) {
        return timetableDaoDao.delete(courseId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Timetable> findById(Long courseId) {
        return timetableDaoDao.findById(courseId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Integer> getStartTimesOf(Long courseId){
        List<Integer> startTimes = new ArrayList<>();
        for (String h : hours){
            startTimes.add(null);
        }
        for (Timetable timetable : findById(courseId)){
            startTimes.add(timetable.getDayOfWeek(), timetable.getBegins().getHour() * 100 + timetable.getBegins().getMinute());
        }
        return startTimes;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Integer> getEndTimesOf(Long courseId){
        List<Integer> endTimes = new ArrayList<>();
        for (String h : hours){
            endTimes.add(null);
        }
        for (Timetable timetable : findById(courseId)){
            endTimes.add(timetable.getDayOfWeek(), timetable.getEnd().getHour() * 100 + timetable.getEnd().getMinute());
        }
        return endTimes;
    }


    @Transactional(readOnly = true)
    @Override
    public Timetable[] findByIdOrdered(Long courseId){
        Timetable[]  timeTables = new Timetable[7];
        List<Timetable> timetableList = timetableDaoDao.findById(courseId);

        for(Timetable timetable: timetableList){
            timeTables[timetable.getDayOfWeek()] = timetable;
        }

        return timeTables;

    }


}
