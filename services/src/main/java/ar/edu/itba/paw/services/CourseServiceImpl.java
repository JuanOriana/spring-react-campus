package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Permissions;
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

    @Autowired
    private UserDao userDao;

    @Override
    public Course create(Integer year, Integer quarter, String board, Long subjectId, String subjectName,
                         String subjectCode) {
        return courseDao.create(year, quarter, board, subjectId, subjectName, subjectCode);
    }

    @Override
    public boolean update(Long id, Course course) {
        return courseDao.update(id, course);
    }

    @Override
    public boolean delete(Long id) {
        return courseDao.delete(id);
    }

    @Override
    public List<Course> list(Long userId) {
        return courseDao.list(userId);
    }

    @Override
    public Optional<Course> getById(Long id) {
        return courseDao.getById(id);
    }

    @Override
    public Map<User, Role> getTeachers(Long courseId) {
        return courseDao.getTeachers(courseId);
    }

    @Override
    public List<User> getStudents(Long courseId) {
        return courseDao.getStudents(courseId);
    }

    @Override
    public boolean belongs(Long userId, Long courseId) {
        return courseDao.belongs(userId, courseId);
    }

    @Override
    public boolean isTeacher(Long userId, Long courseId) {
        Optional<Role> userRole = userDao.getRole(userId, courseId);
        return userRole.isPresent() && userRole.get().getRoleId() == Permissions.TEACHER.getValue();
    }

    @Override
    public boolean isHelper(Long userId, Long courseId) {
        Optional<Role> userRole = userDao.getRole(userId, courseId);
        return userRole.isPresent() && userRole.get().getRoleId() == Permissions.HELPER.getValue();
    }

    @Override
    public boolean isStudent(Long userId, Long courseId) {
        Optional<Role> userRole = userDao.getRole(userId, courseId);
        return userRole.isPresent() && userRole.get().getRoleId() == Permissions.STUDENT.getValue();
    }

    @Override
    public boolean isPrivileged(Long userId, Long courseId) {
        Optional<Role> userRole = userDao.getRole(userId, courseId);
        if(!userRole.isPresent()) return false;
        int roleId = userRole.get().getRoleId();
        return roleId == Permissions.TEACHER.getValue() || roleId == Permissions.HELPER.getValue();
    }

}
