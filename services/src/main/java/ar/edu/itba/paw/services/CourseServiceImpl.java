package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

    @Override
    public boolean create(Course course) {
        return courseDao.create(course);
    }

    @Override
    public boolean update(int id, Course course) {
        return courseDao.update(id, course);
    }

    @Override
    public boolean delete(int id) {
        return courseDao.delete(id);
    }

    @Override
    public List<Course> list() {
        return courseDao.list();
    }

    @Override
    public Course getById(int id) {
        return courseDao.getById(id);
    }
}
