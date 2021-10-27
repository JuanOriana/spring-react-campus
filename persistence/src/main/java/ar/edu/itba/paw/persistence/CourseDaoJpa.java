package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Primary
@Repository
public class CourseDaoJpa extends BasePaginationDaoImpl<Course> implements CourseDao {

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
    public CampusPage<Course> list(Long userId, CampusPageRequest pageRequest) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("userId", userId);
        String query = "SELECT courseId FROM courses NATURAL JOIN user_to_course WHERE userId = :userId ORDER BY year DESC";
        String mappingQuery = "SELECT DISTINCT enrollment.course FROM Enrollment enrollment WHERE enrollment.course.courseId IN (:ids) ORDER BY enrollment.course.year DESC";
        return listBy(properties, query, mappingQuery, pageRequest, Course.class);
    }


    @Override
    public List<Course> listCurrent(Long userId) {
        LocalDateTime time = LocalDateTime.now();
        int quarter = time.getMonthValue() >= Month.JULY.getValue() ? 2 : 1;
        int year = time.getYear();
        TypedQuery<Course> listCoursesTypedQuery = em.createQuery("SELECT enrollment.course FROM Enrollment enrollment WHERE enrollment.user.userId = :userId AND enrollment.course.quarter = :quarter AND enrollment.course.year =:year", Course.class);
        listCoursesTypedQuery.setParameter("userId", userId);
        listCoursesTypedQuery.setParameter("quarter", quarter);
        listCoursesTypedQuery.setParameter("year", year);
        return listCoursesTypedQuery.getResultList();
    }

    @Override
    public Optional<Course> findById(Long id) {
        return Optional.ofNullable(em.find(Course.class, id));
    }

    @Override
    public List<User> getStudents(Long courseId) {
        TypedQuery<User> listUserTypedQuery = em.createQuery("SELECT enrollment.user FROM Enrollment enrollment  WHERE enrollment.course.courseId =:courseId AND enrollment.role.roleId=:roleId", User.class);
        listUserTypedQuery.setParameter("courseId", courseId);
        listUserTypedQuery.setParameter("roleId", Roles.STUDENT.getValue());
        return listUserTypedQuery.getResultList();
    }

    @Override
    public Map<User, Role> getTeachers(Long courseId) {
        Map<User,Role> userRoleMap = new HashMap<>();

        TypedQuery<Role> listRolesTypedQuery = em.createQuery("SELECT role FROM Role role WHERE role.roleId NOT IN (:roleId)", Role.class);
        listRolesTypedQuery.setParameter("roleId", Roles.STUDENT.getValue());
        List<Role> listRoles = listRolesTypedQuery.getResultList();

        for(Role role : listRoles){
            TypedQuery<User> listUserRolesTypedQuery = em.createQuery("SELECT enrollment.user FROM Enrollment enrollment WHERE enrollment.course.courseId = :courseId AND enrollment.role.roleId =:roleId",User.class);
            listUserRolesTypedQuery.setParameter("courseId", courseId);
            listUserRolesTypedQuery.setParameter("roleId", role.getRoleId());
            List<User> userList = listUserRolesTypedQuery.getResultList();
            for(User user: userList){
                userRoleMap.put(user,role);
            }
        }

        return userRoleMap;
    }


    @Override
    public boolean belongs(Long userId, Long courseId) {
        TypedQuery<Course> courseTypedQuery = em.createQuery("SELECT enrollment.course FROM Enrollment enrollment WHERE enrollment.course.courseId=:courseId AND enrollment.user.userId = :userId", Course.class);
        courseTypedQuery.setParameter("userId", userId);
        courseTypedQuery.setParameter("courseId", courseId);
        return !courseTypedQuery.getResultList().isEmpty();
    }

    @Transactional
    @Override
    public boolean enroll(Long userId, Long courseId, Integer roleId) {
        User user = em.find(User.class, userId);
        Course course = em.find(Course.class, courseId);
        Role role = em.find(Role.class, roleId);
        Enrollment enrollment = new Enrollment(user, course, role);
        em.persist(enrollment);
        return true;
    }

    @Override
    public List<User> listUnenrolledUsers(Long courseId) {
        TypedQuery<User> listUserTypedQuery = em.createQuery("SELECT user FROM User user WHERE user.admin = false AND user.userId NOT IN (SELECT enrollment.user.userId FROM  Enrollment enrollment WHERE enrollment.course.courseId = :courseId)", User.class);
        listUserTypedQuery.setParameter("courseId", courseId);
        return listUserTypedQuery.getResultList();
    }

    @Override
    public List<Course> listWhereStudent(Long userId) {
        TypedQuery<Course> listCourseTypedQuery = em.createQuery("SELECT enrollment.course FROM Enrollment enrollment WHERE enrollment.user.userId = :userId AND enrollment.role.roleId = :roleId", Course.class);
        listCourseTypedQuery.setParameter("userId", userId);
        listCourseTypedQuery.setParameter("roleId", Roles.STUDENT.getValue());
        return listCourseTypedQuery.getResultList();
    }

    @Override
    public CampusPage<Course> listByYearQuarter(Integer year, Integer quarter, CampusPageRequest pageRequest) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("year", year);
        properties.put("quarter", quarter);
        String query = "SELECT courseId FROM courses WHERE year = :year AND quarter = :quarter ORDER BY year DESC";
        String mappingQuery = "SELECT DISTINCT c FROM Course c WHERE c.courseId IN (:ids) ORDER BY c.year DESC";
        return listBy(properties, query, mappingQuery, pageRequest, Course.class);
    }

    @Override
    public List<Integer> getAvailableYears() {
        TypedQuery<Integer> listYearsTypedQuery = em.createQuery("SELECT DISTINCT course.year FROM Course course", Integer.class);
        return listYearsTypedQuery.getResultList();
    }

    @Override
    public boolean exists(Integer year, Integer quarter, String board, Long subjectId) {
        TypedQuery<Course> courseTypedQuery = em.createQuery("SELECT course FROM Course course WHERE course.board = :board AND course.quarter = :quarter AND course.year = :year AND course.subject.subjectId = :subjectId", Course.class);
        courseTypedQuery.setParameter("board", board);
        courseTypedQuery.setParameter("year", year);
        courseTypedQuery.setParameter("quarter", quarter);
        courseTypedQuery.setParameter("subjectId", subjectId);
        return !courseTypedQuery.getResultList().isEmpty();
    }

    @Override
    public Long getTotalStudents(Long courseId){
        TypedQuery<Long> totalStudentsTypedQuery = em.createQuery("SELECT COUNT(DISTINCT enrollment.user.userId ) FROM Enrollment enrollment WHERE enrollment.course.courseId = :courseId AND enrollment.role.roleId = :roleId",Long.class);
        totalStudentsTypedQuery.setParameter("courseId", courseId);
        totalStudentsTypedQuery.setParameter("roleId", Roles.STUDENT.getValue());
        return totalStudentsTypedQuery.getSingleResult();
    }
}
