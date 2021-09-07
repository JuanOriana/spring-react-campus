package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class TimeTableDaoImplTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private TimetableDaoImpl timetableDao;

    private JdbcTemplate jdbcTemplate;

    private final int COURSE_ID = 1;
    private final int COURSE_YEAR = 2021;
    private final int COURSE_QUARTER = 2;
    private final String COURSE_BOARD = "S1";

    private final int SUBJECT_ID = 1;
    private final String SUBJECT_CODE = "A1";
    private final String SUBJECT_NAME = "PAW";

    private final int TIME_TABLE_DAY_OF_WEEK = 1;
    private final Time TIME_TABLE_START_OF_COURSE = new Time(TimeUnit.HOURS.toMillis(12));
    private final Time TIME_TABLE_DURATION_OF_COURSE = new Time(TimeUnit.HOURS.toMillis(2));

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "courses");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "subjects");
        String sqlInsertSubject = String.format("INSERT INTO subjects  VALUES (%d, %s, %s)", SUBJECT_ID, SUBJECT_NAME, SUBJECT_CODE);
        String sqlInsertCourse = String.format("INSERT INTO courses  VALUES (%d, %d, %d,%s, %d)", COURSE_ID, SUBJECT_ID, COURSE_QUARTER, COURSE_BOARD, COURSE_YEAR);
        jdbcTemplate.execute(sqlInsertSubject);
        jdbcTemplate.execute(sqlInsertCourse);
    }

    private Course getMockCourse(){
        Subject mockSubject = new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME);
        return new Course(COURSE_ID, COURSE_YEAR, COURSE_QUARTER, COURSE_BOARD, mockSubject);
    }

    @Test
    public void testCreate() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "timetables");
        final boolean timeTableEntryInsertion = timetableDao.create(getMockCourse(), TIME_TABLE_DAY_OF_WEEK, TIME_TABLE_START_OF_COURSE, TIME_TABLE_DURATION_OF_COURSE);
        assertEquals( 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "timetables"));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateNonExistentCourseId() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "timetables");
        Course mockCourse = getMockCourse();
        mockCourse.setCourseId(COURSE_ID + 1);
        timetableDao.create(mockCourse, TIME_TABLE_DAY_OF_WEEK, TIME_TABLE_START_OF_COURSE, TIME_TABLE_DURATION_OF_COURSE);
        Assert.fail("Should have thrown assertion error for non-existent foreign key 'course id' ");
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testUpdateNoExist() {
    }

    @Test
    public void testDelete() {
    }

    @Test
    public void testDeleteNoExist() {
    }

    @Test
    public void testGetById() {
    }

    @Test
    public void getByIdNoExist() {
    }

}
