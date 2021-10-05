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
    private static Long SUBJECT_ID = 1L;
    private static String SUBJECT_CODE = "A1";
    private static String SUBJECT_NAME = "PAW";
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
        Course course = courseService.create(YEAR, QUARTER, BOARD, SUBJECT_ID, startTime, endTime);
        Assert.assertEquals(course.getCourseId(), COURSE_ID);
    }

}
