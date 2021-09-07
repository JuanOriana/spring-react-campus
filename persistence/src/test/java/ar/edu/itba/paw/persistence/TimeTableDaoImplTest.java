package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.Timetable;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

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
    private final Time TIME_TABLE_END_OF_COURSE = new Time(TimeUnit.HOURS.toMillis(14));

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "courses");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "subjects");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "timetables");
        String sqlInsertSubject = String.format("INSERT INTO subjects  VALUES (%d, '%s', '%s')", SUBJECT_ID, SUBJECT_NAME, SUBJECT_CODE);
        String sqlInsertCourse = String.format("INSERT INTO courses  VALUES (%d, %d, %d,'%s', %d)", COURSE_ID, SUBJECT_ID, COURSE_QUARTER, COURSE_BOARD, COURSE_YEAR);
        jdbcTemplate.execute(sqlInsertSubject);
        jdbcTemplate.execute(sqlInsertCourse);
    }

    private Course getMockCourse(){
        Subject mockSubject = new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME);
        return new Course(COURSE_ID, COURSE_YEAR, COURSE_QUARTER, COURSE_BOARD, mockSubject);
    }

    @Test
    public void testCreate() {
        final boolean timeTableEntryInsertion = timetableDao.create(getMockCourse(), TIME_TABLE_DAY_OF_WEEK, TIME_TABLE_START_OF_COURSE, TIME_TABLE_END_OF_COURSE);
        assertEquals( 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "timetables"));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateNonExistentCourseId() {
        Course mockCourse = getMockCourse();
        mockCourse.setCourseId(COURSE_ID + 1);
        timetableDao.create(mockCourse, TIME_TABLE_DAY_OF_WEEK, TIME_TABLE_START_OF_COURSE, TIME_TABLE_END_OF_COURSE);
        Assert.fail("Should have thrown assertion error for non-existent foreign key 'course id' ");
    }

    @Test
    public void testUpdate() {
        String sqlInsertTimeTableEntry = String.format("INSERT INTO timetables (courseId, dayOfWeek, startTime, endTime) VALUES (%d,%d,'%s','%s')",COURSE_ID,TIME_TABLE_DAY_OF_WEEK, TIME_TABLE_START_OF_COURSE.toString(),TIME_TABLE_END_OF_COURSE.toString());
        jdbcTemplate.execute(sqlInsertTimeTableEntry);

        Time startChangedTo = new Time(TimeUnit.HOURS.toMillis(16));
        Time endChangedTo = new Time(TimeUnit.HOURS.toMillis(3));

        final boolean isUpdated = timetableDao.update(COURSE_ID,TIME_TABLE_DAY_OF_WEEK,startChangedTo,endChangedTo);
        assertTrue(isUpdated);

        String sqlGetStartTimeOfCourseId = String.format("SELECT startTime FROM timetables WHERE courseId = %d", COURSE_ID);
        Time startTimeOfCourseIdInDB = jdbcTemplate.queryForObject(sqlGetStartTimeOfCourseId,Time.class);
        assertEquals(startChangedTo, startTimeOfCourseIdInDB);

        String sqlGetDurationOfCourseId = String.format("SELECT endTime FROM timetables WHERE courseId = %d", COURSE_ID);
        Time endTimeOfCourseIdInDB = jdbcTemplate.queryForObject(sqlGetDurationOfCourseId,Time.class);
        assertEquals(endChangedTo, endTimeOfCourseIdInDB);
    }

    @Test(expected = AssertionError.class)
    public void testUpdateNoExist() {
        String sqlInsertTimeTableEntry = String.format("INSERT INTO timetables (courseId, dayOfWeek, startTime, endTime) VALUES (%d,%d,'%s','%s')",COURSE_ID,TIME_TABLE_DAY_OF_WEEK, TIME_TABLE_START_OF_COURSE.toString(),TIME_TABLE_END_OF_COURSE.toString());
        jdbcTemplate.execute(sqlInsertTimeTableEntry);

        Time startChangedTo = new Time(TimeUnit.HOURS.toMillis(16));
        Time durationChangedTo = new Time(TimeUnit.HOURS.toMillis(3));

        final boolean isUpdated = timetableDao.update(COURSE_ID + 1,TIME_TABLE_DAY_OF_WEEK,startChangedTo,durationChangedTo);
        Assert.fail("Should have thrown assertion error for non-existent foreign key 'course id' ");
        assertFalse(isUpdated);
    }

    @Test
    public void testDelete() {
        String sqlInsertTimeTableEntry = String.format("INSERT INTO timetables (courseId, dayOfWeek, startTime, endTime) VALUES (%d,%d,'%s','%s')",COURSE_ID,TIME_TABLE_DAY_OF_WEEK, TIME_TABLE_START_OF_COURSE.toString(),TIME_TABLE_END_OF_COURSE.toString());
        jdbcTemplate.execute(sqlInsertTimeTableEntry);

        final boolean isDeleted = timetableDao.delete(COURSE_ID);
        assertTrue(isDeleted);

        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "timetables"));
    }

    @Test(expected = AssertionError.class)
    public void testDeleteNoExist() {
        String sqlInsertTimeTableEntry = String.format("INSERT INTO timetables (courseId, dayOfWeek, startTime, endTime) VALUES (%d,%d,'%s','%s')",COURSE_ID,TIME_TABLE_DAY_OF_WEEK, TIME_TABLE_START_OF_COURSE.toString(),TIME_TABLE_END_OF_COURSE.toString());
        jdbcTemplate.execute(sqlInsertTimeTableEntry);

        final boolean isDeleted = timetableDao.delete(COURSE_ID + 1);
        Assert.fail("Should have thrown assertion error for non-existent foreign key 'course id' ");
        assertFalse(isDeleted);
    }

    @Test
    public void testGetById() {
        String sqlInsertTimeTableEntry = String.format("INSERT INTO timetables (courseId, dayOfWeek, startTime, endTime) VALUES (%d,%d,'%s','%s')",COURSE_ID,TIME_TABLE_DAY_OF_WEEK, TIME_TABLE_START_OF_COURSE.toString(),TIME_TABLE_END_OF_COURSE.toString());
        jdbcTemplate.execute(sqlInsertTimeTableEntry);

        List<Timetable> timetableOptional = timetableDao.getById(COURSE_ID);
        assertEquals(1, timetableOptional.size());
        assertEquals(COURSE_ID, timetableOptional.get(0).getCourseId());
        assertEquals(TIME_TABLE_DAY_OF_WEEK, timetableOptional.get(0).getDayOfWeek());
        assertEquals(TIME_TABLE_START_OF_COURSE, timetableOptional.get(0).getBegins());
        assertEquals(TIME_TABLE_END_OF_COURSE, timetableOptional.get(0).getEnd());
    }

    @Test(expected = AssertionError.class)
    public void getByIdNoExist() {
        String sqlInsertTimeTableEntry = String.format("INSERT INTO timetables (courseId, dayOfWeek, startTime, endTime) VALUES (%d,%d,'%s','%s')",COURSE_ID,TIME_TABLE_DAY_OF_WEEK, TIME_TABLE_START_OF_COURSE.toString(),TIME_TABLE_END_OF_COURSE.toString());
        jdbcTemplate.execute(sqlInsertTimeTableEntry);

        List<Timetable> timetableOptional = timetableDao.getById(COURSE_ID + 1);
        Assert.fail("Should have thrown assertion error for non-existent foreign key 'course id' ");
        assertEquals(0, timetableOptional.size());
    }

}
