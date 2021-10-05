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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Rollback
@Transactional
public class TimeTableDaoImplTest extends BasicPopulator {

    @Autowired
    private TimetableDaoImpl timetableDao;


    @Before
    public void setUp() {
        super.setUp();
        insertSubject(SUBJECT_ID, SUBJECT_NAME, SUBJECT_CODE);
        insertCourse(COURSE_ID, SUBJECT_ID, COURSE_QUARTER, COURSE_BOARD, COURSE_YEAR);
        insertTimeTable(COURSE_ID, TIME_TABLE_DAY_OF_WEEK, TIME_TABLE_START_OF_COURSE.toString(), TIME_TABLE_END_OF_COURSE.toString());
    }

    private Course getMockCourse() {
        return new Course.Builder()
                .withCourseId(COURSE_ID)
                .withYear(COURSE_YEAR)
                .withQuarter(COURSE_QUARTER)
                .withBoard(COURSE_BOARD)
                .withSubject(new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME))
                .build();
    }

    @Test
    public void testCreate() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "timetables");
        final boolean timeTableEntryInsertion = timetableDao.create(getMockCourse(), TIME_TABLE_DAY_OF_WEEK, TIME_TABLE_START_OF_COURSE, TIME_TABLE_END_OF_COURSE);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "timetables"));
        assertTrue(timeTableEntryInsertion);
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
        Time startChangedTo = new Time(TimeUnit.HOURS.toMillis(16));
        Time endChangedTo = new Time(TimeUnit.HOURS.toMillis(3));

        final boolean isUpdated = timetableDao.update(COURSE_ID, TIME_TABLE_DAY_OF_WEEK, startChangedTo, endChangedTo);
        assertTrue(isUpdated);

        String sqlGetStartTimeOfCourseId = String.format("SELECT startTime FROM timetables WHERE courseId = %d", COURSE_ID);
        Time startTimeOfCourseIdInDB = jdbcTemplate.queryForObject(sqlGetStartTimeOfCourseId, Time.class);
        assertEquals(startChangedTo, startTimeOfCourseIdInDB);

        String sqlGetDurationOfCourseId = String.format("SELECT endTime FROM timetables WHERE courseId = %d", COURSE_ID);
        Time endTimeOfCourseIdInDB = jdbcTemplate.queryForObject(sqlGetDurationOfCourseId, Time.class);
        assertEquals(endChangedTo, endTimeOfCourseIdInDB);
    }

    @Test(expected = AssertionError.class)
    public void testUpdateNoExist() {
        Time startChangedTo = new Time(TimeUnit.HOURS.toMillis(16));
        Time durationChangedTo = new Time(TimeUnit.HOURS.toMillis(3));

        final boolean isUpdated = timetableDao.update(COURSE_ID + 1, TIME_TABLE_DAY_OF_WEEK, startChangedTo, durationChangedTo);
        Assert.fail("Should have thrown assertion error for non-existent foreign key 'course id' ");
        assertFalse(isUpdated);
    }

    @Test
    public void testDelete() {
        final boolean isDeleted = timetableDao.delete(COURSE_ID);
        assertTrue(isDeleted);

        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "timetables"));
    }

    @Test(expected = AssertionError.class)
    public void testDeleteNoExist() {
        final boolean isDeleted = timetableDao.delete(COURSE_ID + 1);
        Assert.fail("Should have thrown assertion error for non-existent foreign key 'course id' ");
        assertFalse(isDeleted);
    }

    @Test
    public void testGetById() {
        List<Timetable> timetableOptional = timetableDao.findById(COURSE_ID);
        assertEquals(1, timetableOptional.size());
        assertEquals(COURSE_ID, timetableOptional.get(0).getCourseId());
        assertEquals(TIME_TABLE_DAY_OF_WEEK, timetableOptional.get(0).getDayOfWeek());
        assertEquals(TIME_TABLE_START_OF_COURSE, timetableOptional.get(0).getBegins());
        assertEquals(TIME_TABLE_END_OF_COURSE, timetableOptional.get(0).getEnd());
    }

    @Test(expected = AssertionError.class)
    public void getByIdNoExist() {
        List<Timetable> timetableOptional = timetableDao.findById(COURSE_ID + 1);
        Assert.fail("Should have thrown assertion error for non-existent foreign key 'course id' ");
        assertEquals(0, timetableOptional.size());
    }

}
