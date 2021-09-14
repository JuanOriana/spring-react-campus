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
    public Course create(Integer year, Integer quarter, String board, Integer subjectId, String subjectName,
                         String subjectCode) {
        return courseDao.create(year, quarter, board, subjectId, subjectName, subjectCode);
    }

    @Override
    public boolean update(Integer id, Course course) {
        return courseDao.update(id, course);
    }

    @Override
    public boolean delete(Integer id) {
        return courseDao.delete(id);
    }

    @Override
    public List<Course> list(Integer userId) {
        return courseDao.list(userId);
    }

    @Override
    public Optional<Course> getById(Integer id) {
        return courseDao.getById(id);
    }

    @Override
    public Map<User, Role> getTeachers(Integer courseId) {
        return courseDao.getTeachers(courseId);
    }

    @Override
    public boolean belongs(Integer userId, Integer courseId) {
        return courseDao.belongs(userId, courseId);
    }

}
