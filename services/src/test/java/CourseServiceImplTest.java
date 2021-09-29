import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.TimetableService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.Errors;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.services.CourseServiceImpl;
import ar.edu.itba.paw.services.TimetableServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Time;
import java.time.Year;
import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceImplTest {

    private static Long COURSE_ID = 1L;
    private static Long USER_ID = 1L;
    private static Long SUBJECT_ID = 1L;
    private static String SUBJECT_CODE = "A1";
    private static String SUBJECT_NAME = "PAW";
    private static Long INVALID_COURSE_ID = 999L;
    private static int YEAR = 2021;
    private static int QUARTER = 2;
    private static String BOARD = "S1";
    private Course getMockCourse() {
        return new Course.Builder()
                .withCourseId(COURSE_ID)
                .withYear(YEAR)
                .withQuarter(QUARTER)
                .withBoard(BOARD)
                .withSubject(new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME))
                .build();
    }

    @InjectMocks
    private final CourseServiceImpl courseService = new CourseServiceImpl();

    @Mock
    private CourseDao mockDao;

    @Mock
    private TimetableService timetableService;

    @Test
    public void testCreateCourse() {
        when(mockDao.create(YEAR, QUARTER, BOARD, SUBJECT_ID)).thenReturn(new Course.Builder()
                .withCourseId(COURSE_ID)
                .withYear(YEAR)
                .withQuarter(QUARTER)
                .withBoard(BOARD)
                .withSubject(new Subject(SUBJECT_ID, SUBJECT_CODE, SUBJECT_NAME))
                .build());
        Integer[] start = {0, 0, 0, 0, 0, 0};
        Integer[] end = {0, 0, 0, 0, 0, 0};
        List<Integer> startTime = new ArrayList<>(Arrays.asList(start));
        List<Integer> endTime = new ArrayList<>(Arrays.asList(end));
        Either<Course, Collection<Errors>> newCourse = courseService.create(YEAR, QUARTER, BOARD, SUBJECT_ID, startTime, endTime);
        Assert.assertTrue(newCourse.isValuePresent());
        Course course = newCourse.getValue();
        Assert.assertEquals(course.getCourseId(), COURSE_ID);
    }

    @Test
    public void testFindByCourseId() {
        Course course = getMockCourse();
        when(mockDao.getById(COURSE_ID)).thenReturn(Optional.of(course));
        final Optional<Course> queriedCourse = courseService.getById(COURSE_ID);
        Assert.assertTrue(queriedCourse.isPresent());
        Assert.assertEquals(COURSE_ID, queriedCourse.get().getCourseId());
    }

    @Test
    public void testUpdate() {
        Course course = getMockCourse();
        when(mockDao.update(COURSE_ID, course)).thenReturn(true);
        boolean courseUpdateResult = courseService.update(COURSE_ID, course);
        Assert.assertTrue(courseUpdateResult);
    }

    @Test
    public void testUpdateDoesNotExist() {
        Course course = getMockCourse();
        when(mockDao.update(INVALID_COURSE_ID, course)).thenReturn(false);
        boolean courseUpdateResult = courseService.update(INVALID_COURSE_ID, course);
        Assert.assertFalse(courseUpdateResult);
    }

    @Test
    public void testDelete() {
        when(mockDao.delete(COURSE_ID)).thenReturn(true);
        boolean courseUpdateResult = courseService.delete(COURSE_ID);
        Assert.assertTrue(courseUpdateResult);
    }

    @Test
    public void testDeleteDoesNotExit() {
        when(mockDao.delete(INVALID_COURSE_ID)).thenReturn(false);
        boolean courseUpdateResult = courseService.delete(INVALID_COURSE_ID);
        Assert.assertFalse(courseUpdateResult);
    }

    @Test
    public void testList() {
        Course course = getMockCourse();
        when(mockDao.list(USER_ID)).thenReturn(new ArrayList<Course>(){{ add(course); }});
        List<Course> courses = courseService.list(USER_ID);
        Assert.assertTrue(courses.size() > 0);
        Assert.assertEquals(course.getCourseId(), courses.get(0).getCourseId());
    }

}
