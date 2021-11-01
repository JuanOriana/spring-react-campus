package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.TimetableDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.Timetable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
@Transactional
public class TimeTableDaoImplTest {

    private final Long COURSE_ID = 1L;
    private final Integer COURSE_YEAR = 2021;
    private final Integer COURSE_QUARTER = 1;
    private final String COURSE_BOARD = "S1";

    private final Long SUBJECT_ID = 1L;
    private final String SUBJECT_CODE = "A1";
    private final String SUBJECT_NAME = "Protos";

    protected final Integer TIME_TABLE_DAY_OF_WEEK = 1;
    protected final LocalTime TIME_TABLE_START_OF_COURSE = LocalTime.of(12, 0);
    protected final LocalTime TIME_TABLE_END_OF_COURSE = LocalTime.of(14, 0);

    @Autowired
    private TimetableDao timetableDao;

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
        final boolean timeTableEntryInsertion = timetableDao.create(getMockCourse(), TIME_TABLE_DAY_OF_WEEK, TIME_TABLE_START_OF_COURSE, TIME_TABLE_END_OF_COURSE);
        assertTrue(timeTableEntryInsertion);
    }

    @Test(expected = AssertionError.class)
    public void testCreateNonExistentCourseId() {
        Course mockCourse = getMockCourse();
        mockCourse.setCourseId(COURSE_ID + 1);
        timetableDao.create(mockCourse, TIME_TABLE_DAY_OF_WEEK, TIME_TABLE_START_OF_COURSE, TIME_TABLE_END_OF_COURSE);
        Assert.fail("Should have thrown assertion error for non-existent foreign key 'course id' ");
    }

    @Test(expected = AssertionError.class)
    public void testUpdateNoExist() {
        LocalTime startChangedTo = LocalTime.of(16, 0);
        LocalTime durationChangedTo = LocalTime.of(3, 0);
        final boolean isUpdated = timetableDao.update(COURSE_ID + 1, TIME_TABLE_DAY_OF_WEEK, startChangedTo, durationChangedTo);
        Assert.fail("Should have thrown assertion error for non-existent foreign key 'course id' ");
    }


    @Test(expected = AssertionError.class)
    public void testDeleteNoExist() {
        final boolean isDeleted = timetableDao.delete(COURSE_ID + 1);
        Assert.fail("Should have thrown assertion error for non-existent foreign key 'course id' ");
    }


    @Test(expected = AssertionError.class)
    public void getByIdNoExist() {
        List<Timetable> timetableOptional = timetableDao.findById(COURSE_ID + 1);
        Assert.fail("Should have thrown assertion error for non-existent foreign key 'course id' ");
    }

}
