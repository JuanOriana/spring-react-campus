package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Rollback
@Transactional
public class CourseDaoImplTest extends BasicPopulator {

    @Autowired
    private CourseDaoImpl courseDao;


    private static final RowMapper<Course> COURSE_ROW_MAPPER = (rs, rowNum) ->
            new Course.Builder()
                    .withCourseId(rs.getLong("courseId"))
                    .withYear(rs.getInt("year"))
                    .withQuarter(rs.getInt("quarter"))
                    .withBoard(rs.getString("board"))
                    .withSubject(new Subject(rs.getLong("subjectId"), rs.getString("code"),
                            rs.getString("subjectName")))
                    .build();

    private static final RowMapper<Role> USER_TO_COURSE_ROW_MAPPER = (rs, rowNum) ->
            new Role.Builder().withRoleId(rs.getInt("roleid")).withRoleName(rs.getString("rolename")).build();


    private User getMockUser() {
        return new User.Builder()
                .withUserId(USER_ID)
                .withFileNumber(USER_FILE_NUMBER)
                .withName(USER_NAME)
                .withSurname(USER_SURNAME)
                .withUsername(USER_USERNAME)
                .withEmail(USER_EMAIL)
                .withPassword(USER_PASSWORD)
                .isAdmin(USER_IS_ADMIN)
                .build();
    }

    private Role getMockRole() {
        return new Role.Builder().withRoleId(TEACHER_ROLE_ID).withRoleName(TEACHER_ROLE_NAME).build();
    }

    @Before
    public void setUp() {
        super.setUp();
        insertSubject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME);
        insertUser(USER_ID, USER_FILE_NUMBER, USER_NAME, USER_SURNAME, USER_USERNAME, USER_EMAIL, USER_PASSWORD, USER_IS_ADMIN);
        insertRole(STUDENT_ROLE_ID, STUDENT_ROLE_NAME);
        insertCourse(COURSE_ID, SUBJECT_ID, COURSE_QUARTER, COURSE_BOARD, COURSE_YEAR);
        insertUserToCourse(COURSE_ID, USER_ID, STUDENT_ROLE_ID);
    }

    @Test
    public void testCreate() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "courses");
        Course course = courseDao.create(COURSE_YEAR, COURSE_QUARTER, COURSE_BOARD, SUBJECT_ID);
        assertNotNull(course);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses"));
    }

    @Test(expected = RuntimeException.class)
    public void testCreateDuplicateUniqueValues() {
        courseDao.create(COURSE_YEAR, COURSE_QUARTER, COURSE_BOARD, SUBJECT_ID);
        courseDao.create(COURSE_YEAR, COURSE_QUARTER, COURSE_BOARD, SUBJECT_ID);
        Assert.fail("Should have thrown Runtime Exception for duplicate constraint");
    }

    @Test
    public void testDelete() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses"));
        courseDao.delete(COURSE_ID);
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses"));
    }

    @Test
    public void testDeleteNoExist() {
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses"));
        assertFalse(courseDao.delete(INVALID_COURSE_ID));
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses"));
    }

    @Test
    public void testGetById() {
        final Optional<Course> course = courseDao.findById(COURSE_ID);
        assertNotNull(course);
        assertTrue(course.isPresent());
        assertEquals(COURSE_ID, course.get().getCourseId());
    }

    @Test
    public void testGetByIdNoExist() {
        final Optional<Course> course = courseDao.findById(INVALID_COURSE_ID);
        assertNotNull(course);
        assertFalse(course.isPresent());
    }

    @Test
    public void testList() {
        final List<Course> list = courseDao.list(USER_ID);
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    public void testEmptyList() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "courses");
        final List<Course> list = courseDao.list(USER_ID);
        assertNotNull(list);
        assertEquals(0, list.size());
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

        Course courseDb = jdbcTemplate.query("SELECT * FROM courses NATURAL JOIN subjects WHERE courseId = ?", new Object[]{COURSE_ID}, COURSE_ROW_MAPPER).get(0);
        assertNotNull(courseDb);
        assertEquals(COURSE_ID, courseDb.getCourseId());
        assertEquals(UPDATE_QUARTER, courseDb.getQuarter());
        assertEquals(COURSE_BOARD, courseDb.getBoard());
    }

    @Test
    public void testGetStudents() {
        List<User> list = courseDao.getStudents(COURSE_ID);

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals(USER_ID, list.get(0).getUserId());
    }

    @Test
    public void testGetTeachers() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "user_to_course");
        insertRole(TEACHER_ROLE_ID, TEACHER_ROLE_NAME);
        insertUserToCourse(COURSE_ID, USER_ID, TEACHER_ROLE_ID);
        Map<User, Role> userRoleMap = courseDao.getTeachers(COURSE_ID);
        User user = getMockUser();
        Role teacherRole = getMockRole();
        assertNotNull(userRoleMap);
        assertEquals(teacherRole, userRoleMap.get(user));

    }

    @Test
    public void testEnroll() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "user_to_course");
        assertTrue(courseDao.enroll(USER_ID, COURSE_ID, STUDENT_ROLE_ID));
        Role role = jdbcTemplate.query("SELECT * FROM user_to_course NATURAL JOIN roles WHERE userid=? AND courseid = ?", new Object[]{USER_ID, COURSE_ID}, USER_TO_COURSE_ROW_MAPPER).get(0);
        assertNotNull(role);
        assertEquals(STUDENT_ROLE_ID, role.getRoleId());
    }

}
