package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class CourseDaoImplTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private CourseDaoImpl courseDao;

    private JdbcTemplate jdbcTemplate;

    private final Long COURSE_ID = 1L;
    private final Integer SUBJECT_ID = 1;
    private final Long USER_ID = 1L;
    private final Integer USER_FILENUMBER = 49523123;
    private final Integer ROLE_ID = 1;
    private final Integer QUARTER = 1;
    private final Integer YEAR = 2021;
    private final String SUBJECT_NAME = "PAW";
    private final String SUBJECT_CODE = "A1";
    private final String BOARD = "S1";
    private final Long INVALID_COURSE_ID = 999L;
    private final String insertCourseSql = String.format("INSERT INTO courses (subjectId, quarter,board,year) VALUES (%d, %d,'S1',%d)", SUBJECT_ID, QUARTER,YEAR);
    private final String insertCourseWithIdSql = String.format("INSERT INTO courses  VALUES (%d, %d, %d, 'S1',%d)", COURSE_ID, SUBJECT_ID, QUARTER, YEAR);
    private final String insertSubjectSql = String.format("INSERT INTO subjects (subjectId,code,subjectName) VALUES (%d,'A1','PAW')", SUBJECT_ID);
    private final String insertUserSql = String.format("INSERT INTO users VALUES (%d, %d,'John','Doe','johndoe', 'johndoe@gmail.com', 'asd123', %b)", USER_ID, USER_FILENUMBER, true);
    private final String insertUserToCourseSql = String.format("INSERT INTO user_to_course VALUES (%d,%d,%d)", COURSE_ID, USER_ID, ROLE_ID);
    String insertRoleSql = String.format("INSERT INTO roles VALUES (%d, 'Student')", ROLE_ID);
    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "courses");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "subjects");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "roles");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "user_to_course");
        jdbcTemplate.execute(insertSubjectSql);
        jdbcTemplate.execute(insertUserSql);
        jdbcTemplate.execute(insertRoleSql);
        jdbcTemplate.execute(insertCourseWithIdSql);
        jdbcTemplate.execute(insertUserToCourseSql);
    }

    @Test
    public void testCreate() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "courses");
        Course course = courseDao.create(YEAR, QUARTER, BOARD, SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME);
        assertNotNull(course);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses"));
    }

    @Test(expected = RuntimeException.class)
    public void testCreateDuplicateUniqueValues() {
        final Course isCreated1 = courseDao.create(YEAR, QUARTER, BOARD, SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME);
        final Course isCreated2 = courseDao.create(YEAR, QUARTER, BOARD, SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME);
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
        assertFalse(courseDao.delete(INVALID_COURSE_ID)); // magic number
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "courses"));
    }

    @Test
    public void testGetById() {
        final Optional<Course> course = courseDao.getById(COURSE_ID);
        assertNotNull(course);
        assertTrue(course.isPresent());
        assertEquals(COURSE_ID, course.get().getCourseId());
    }

    @Test
    public void testGetByIdNoExist() {
        final Optional<Course> course = courseDao.getById(INVALID_COURSE_ID);
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
    public void testUpdate(){
        assertTrue(courseDao.update(COURSE_ID, new Course.Builder()
                .withCourseId(COURSE_ID)
                .withYear(YEAR)
                .withQuarter(QUARTER)
                .withBoard(BOARD)
                .withSubject( new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME))
                .build()));
        final Optional<Course> course = courseDao.getById(COURSE_ID);
        assertNotNull(course);
        assertTrue(course.isPresent());
        assertEquals(Optional.of(QUARTER).get(), course.get().getQuarter());
        assertEquals("S1", course.get().getBoard());
        assertEquals(Optional.of(YEAR).get(), course.get().getYear());
    }

}
