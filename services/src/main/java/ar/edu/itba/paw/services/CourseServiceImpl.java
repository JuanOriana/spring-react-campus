package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

    @Override
    public Course create(Course course) {
        return courseDao.create(course);
    }

    @Override
    public boolean update(long id, Course course) {
        return courseDao.update(id, course);
    }

    @Override
    public boolean delete(long id) {
        return courseDao.delete(id);
    }

    @Override
    public List<Course> list() {
        return courseDao.list();
    }

    @Override
    public Optional<Course> getById(long id) {
        return courseDao.getById(id);
    }

    @Override
    public Map<User, Role> getTeachers(long courseId) {
        return courseDao.getTeachers(courseId);
    }

}
