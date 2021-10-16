package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Primary
@Repository
public class CourseDaoJpa implements CourseDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Course create(Integer year, Integer quarter, String board, Long subjectId) {
        return null;
    }

    @Override
    public boolean update(Long id, Course course) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<Course> list() {
        return null;
    }

    @Override
    public List<Course> list(Long userId) {
        return null;
    }

    @Override
    public List<Course> listCurrent(Long userId) {
        return null;
    }

    @Override
    public Optional<Course> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> getStudents(Long courseId) {
        return null;
    }

    @Override
    public Map<User, Role> getTeachers(Long courseId) {
        return null;
    }

    @Override
    public boolean belongs(Long userId, Long courseId) {
        return false;
    }

    @Override
    public boolean enroll(Long userId, Long courseId, Integer roleId) {
        return false;
    }

    @Override
    public List<User> listUnenrolledUsers(Long courseId) {
        return null;
    }

    @Override
    public List<Course> listWhereStudent(Long userId) {
        return null;
    }

    @Override
    public List<Course> listByYearQuarter(Integer year, Integer quarter) {
        return null;
    }

    @Override
    public List<Integer> getAvailableYears() {
        return null;
    }
}
