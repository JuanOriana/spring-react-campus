package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Primary
@Repository
public class CourseDaoJpa implements CourseDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Course create(Integer year, Integer quarter, String board, Long subjectId) {
        final Course course = new Course(year, quarter, board, new Subject(subjectId, null, null));
        em.persist(course);
        return course;
    }

    @Transactional
    @Override
    public boolean update(Long id, Course course) {
        Optional<Course> dbCourse = findById(id);
        if (!dbCourse.isPresent()) return false;
        dbCourse.get().merge(course);
        em.flush();
        return true;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        Optional<Course> dbCourse = findById(id);
        if (!dbCourse.isPresent()) return false;
        em.remove(dbCourse.get());
        return true;
    }

    @Override
    public List<Course> list() {
        TypedQuery<Course> listCoursesTypedQuery = em.createQuery("SELECT c From Course c", Course.class);
        return listCoursesTypedQuery.getResultList();
    }

    @Override
    public List<Course> list(Long userId) {
        TypedQuery<Course> listCoursesTypedQuery = em.createQuery("SELECT course FROM UserToCourse userToCourse WHERE userToCourse.user.userId = :userId", Course.class);
        listCoursesTypedQuery.setParameter("userId", userId);
        return listCoursesTypedQuery.getResultList();
    }

    @Override
    public List<Course> listCurrent(Long userId) {
        LocalDateTime time = LocalDateTime.now();
        int quarter = time.getMonthValue() >= Month.JULY.getValue() ? 2 : 1;
        int year = time.getYear();
        TypedQuery<Course> listCoursesTypedQuery = em.createQuery("SELECT userToCourse.course FROM UserToCourse userToCourse WHERE userToCourse.user.userId = :userId AND userToCourse.course.quarter = :quarter AND userToCourse.course.year =:year", Course.class);
        listCoursesTypedQuery.setParameter("userId", userId);
        listCoursesTypedQuery.setParameter("quarter", quarter);
        listCoursesTypedQuery.setParameter("year", year);
        List<Course> list = listCoursesTypedQuery.getResultList();
        return list;
    }

    @Override
    public Optional<Course> findById(Long id) {
        return Optional.ofNullable(em.find(Course.class, id));
    }

    @Override
    public List<User> getStudents(Long courseId) {
        TypedQuery<User> listUserTypedQuery = em.createQuery("SELECT userToCourse.user FROM UserToCourse userToCourse  WHERE userToCourse.course.courseId =:courseId AND userToCourse.role.roleId=:roleId", User.class);
        listUserTypedQuery.setParameter("courseId", courseId);
        listUserTypedQuery.setParameter("roleId", Roles.STUDENT.getValue());
        return listUserTypedQuery.getResultList();
    }

    @Override
    public Map<User, Role> getTeachers(Long courseId) {
        return new HashMap<>(); //TODO
    }

    @Override
    public boolean belongs(Long userId, Long courseId) {
        TypedQuery<Course> courseTypedQuery = em.createQuery("SELECT course FROM UserToCourse userToCourse WHERE userToCourse.course.courseId=:courseId AND userToCourse.user.userId = :userId", Course.class);
        courseTypedQuery.setParameter("userId", userId);
        courseTypedQuery.setParameter("courseId", courseId);
        return courseTypedQuery.getResultList().isEmpty();
    }

    @Transactional
    @Override
    public boolean enroll(Long userId, Long courseId, Integer roleId) {
        UserToCourse userToCourse = new UserToCourse(em.find(User.class, userId), em.find(Course.class, courseId), em.find(Role.class, roleId));
        em.persist(userToCourse);
        return true;
    }

    @Override
    public List<User> listUnenrolledUsers(Long courseId) {
        TypedQuery<User> listUserTypedQuery = em.createQuery("SELECT user FROM User user WHERE user.admin = false AND user.userId NOT IN (SELECT userToCourse.user.userId FROM  UserToCourse userToCourse WHERE userToCourse.course.courseId = :courseId)", User.class);
        listUserTypedQuery.setParameter("courseId", courseId);
        return listUserTypedQuery.getResultList();
    }

    @Override
    public List<Course> listWhereStudent(Long userId) {
        TypedQuery<Course> listCourseTypedQuery = em.createQuery("SELECT userToCourse.course FROM UserToCourse userToCourse WHERE userToCourse.user.userId = :userId AND userToCourse.role.roleId = :roleId", Course.class);
        listCourseTypedQuery.setParameter("userId", userId);
        listCourseTypedQuery.setParameter("roleId", Roles.STUDENT.getValue());
        return listCourseTypedQuery.getResultList();
    }

    @Override
    public List<Course> listByYearQuarter(Integer year, Integer quarter) {
        TypedQuery<Course> listCourseTypedQuery = em.createQuery("SELECT course FROM Course course WHERE course.year =:year AND course.quarter = :quarter", Course.class);
        listCourseTypedQuery.setParameter("year", year);
        listCourseTypedQuery.setParameter("quarter", quarter);
        return listCourseTypedQuery.getResultList();
    }

    @Override
    public List<Integer> getAvailableYears() {
        TypedQuery<Integer> listYearsTypedQuery = em.createQuery("SELECT DISTINCT course.year FROM Course course", Integer.class);
        return listYearsTypedQuery.getResultList();
    }
}
