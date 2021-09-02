import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.services.CourseServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceImplTest {

    private static long COURSE_ID = 1;
    private static int YEAR = 2021;
    private static int QUARTER = 2;
    private static String CODE = "122A";
    private static String BOARD = "S1";
    private static String NAME = "Proyecto de Aplicaciones Web";
    private Course getMockCourse() {
        return new Course(COURSE_ID,YEAR, CODE, QUARTER, BOARD, NAME);
    }

    @InjectMocks
    private CourseServiceImpl courseService = new CourseServiceImpl();

    @Mock
    private CourseDao mockDao;

    @Test
    public void testCreateCourse() {
        Course course = getMockCourse();
        when(mockDao.create(eq(course))).thenReturn(true);
        boolean courseCreateResult = courseService.create(course);
        Assert.assertTrue(courseCreateResult);
    }

    @Test
    public void testFindByCourseId() {
        Course course = getMockCourse();
        when(mockDao.getById(eq((int)COURSE_ID))).thenReturn(Optional.of(course));
        final Optional<Course> queriedCourse = courseService.getById((int)COURSE_ID);

        Assert.assertTrue(queriedCourse.isPresent());
        Assert.assertEquals(COURSE_ID, queriedCourse.get().getSubjectId());
    }
}
