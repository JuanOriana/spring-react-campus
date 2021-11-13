package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:populators/course_populator.sql")
@Rollback
@Transactional
public class CourseDaoImplTest extends BasicPopulator {

    @Autowired
    private CourseDao courseDao;

    private final Long STUDENT_USER_ID = 2L;


    @Test
    public void testCreate() {
        Course course = courseDao.create(COURSE_YEAR, COURSE_QUARTER, COURSE_BOARD, SUBJECT_ID);
        assertNotNull(course);
    }


    @Test
    public void testDelete() {
        assertTrue(courseDao.delete(COURSE_ID));
    }

    @Test
    public void testDeleteNoExist() {
        assertFalse(courseDao.delete(INVALID_COURSE_ID));
    }

    @Test
    public void testFindById() {
        final Optional<Course> course = courseDao.findById(COURSE_ID);
        assertNotNull(course);
        assertTrue(course.isPresent());
        assertEquals(COURSE_ID, course.get().getCourseId());
    }

    @Test
    public void testFindByIdNoExist() {
        final Optional<Course> course = courseDao.findById(INVALID_COURSE_ID);
        assertNotNull(course);
        assertFalse(course.isPresent());
    }


    @Test
    public void testUpdate() {
        Course course = new Course.Builder()
                .withCourseId(COURSE_ID)
                .withYear(COURSE_YEAR)
                .withQuarter(UPDATE_QUARTER)
                .withBoard(COURSE_BOARD)
                .withSubject(new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME))
                .build();
        assertTrue(courseDao.update(COURSE_ID, course));
    }

    @Test
    public void testGetStudents() {
        List<User> list = courseDao.getStudents(COURSE_ID);

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(Long.valueOf(STUDENT_USER_ID), list.get(0).getUserId());
    }

    @Test
    public void testEnroll() {
        assertTrue(courseDao.enroll(USER_ID, COURSE_ID, STUDENT_ROLE_ID));
    }

    @Test
    public void testList() {
        List<Course> list = courseDao.list();

        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        assertEquals(COURSE_ID, list.get(0).getCourseId());
    }

    @Test
    public void testListPaged() {
        CampusPage<Course> page = courseDao.list(USER_ID, new CampusPageRequest(PAGE, PAGE_SIZE));

        assertFalse(page.getContent().isEmpty());
        assertEquals(COURSE_ID, page.getContent().get(0).getCourseId());
    }
    
    @Test
    public void testGetTeachers() {
        User user = new User.Builder()
                .withUserId(USER_ID)
                .withFileNumber(USER_FILE_NUMBER)
                .withName(USER_UPDATE_NAME)
                .withSurname(USER_SURNAME)
                .withUsername(USER_USERNAME)
                .withEmail(USER_EMAIL)
                .withPassword(USER_PASSWORD)
                .isAdmin(USER_IS_ADMIN)
                .build();
        Role role = new Role.Builder().withRoleId(TEACHER_ROLE_ID).withRoleName(TEACHER_ROLE_NAME).build();

        Map<User, Role> teachersMap = courseDao.getPrivilegedUsers(COURSE_ID);

        assertFalse(teachersMap.isEmpty());
        assertEquals(role.getRoleId(), teachersMap.get(user).getRoleId());
    }

    @Test
    public void testBelongs() {
        assertTrue(courseDao.belongs(USER_ID, COURSE_ID));
    }

    @Test
    public void testUnenrolledUsers() {
        List<User> list = courseDao.listUnenrolledUsers(COURSE_ID);

        assertFalse(list.isEmpty());
        assertEquals(Long.valueOf(3L), list.get(0).getUserId());
    }

    @Test
    public void testListWhereStudent() {
        List<Course> courseList = courseDao.listWhereStudent(STUDENT_USER_ID);

        assertFalse(courseList.isEmpty());
        assertEquals(COURSE_ID, courseList.get(0).getCourseId());
    }

    @Test
    public void testListByYearQuarter() {
        CampusPage<Course> campusPage = courseDao.listByYearQuarter(COURSE_YEAR, COURSE_QUARTER, new CampusPageRequest(PAGE, PAGE_SIZE));

        assertFalse(campusPage.getContent().isEmpty());
        assertEquals(COURSE_ID, campusPage.getContent().get(0).getCourseId());
    }

    @Test
    public void testExists() {
        assertTrue(courseDao.exists(COURSE_YEAR, COURSE_QUARTER, COURSE_BOARD, SUBJECT_ID));
    }

    @Test
    public void testGetTotalsStudents() {
        assertEquals(Long.valueOf(1L), courseDao.getTotalStudents(COURSE_ID));
    }

}
